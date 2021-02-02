package net.airgame.bukkit.essential.listener;

import net.airgame.bukkit.essential.EssentialsPlugin;
import net.airgame.bukkit.essential.config.ItemSorter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * 背包整理监听器
 */
public final class InventoryTweaksListener implements Listener {
    private final HashMap<UUID, Long> lastClickTime;
    private final HashSet<UUID> notifiedPlayer;

    public InventoryTweaksListener() {
        lastClickTime = new HashMap<>();
        notifiedPlayer = new HashSet<>();
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            // 如果玩家点击的不是GUI外面的空白处则不处理
            return;
        }
        if (!event.isLeftClick()) {
            // 如果不是左键点击则不处理
            return;
        }

        HumanEntity human = event.getWhoClicked();
        UUID uuid = human.getUniqueId(); //玩家的UUID

        //双击判定，计算两次点击的毫秒差
        long now = System.currentTimeMillis();
        long lastTime = lastClickTime.getOrDefault(uuid, 0L);
        lastClickTime.put(uuid, now);
        if (now >= lastTime + 500) {
            // 要求在500毫秒内点击两次GUI空白处才会进行背包整理
            return;
        }

        Inventory topInventory = event.getView().getTopInventory();
        Inventory bottomInventory = event.getView().getBottomInventory();

        int startIndex; // 整理的背包槽位范围
        int endIndex; // 整理的背包槽位范围
        Inventory sortInventory; // 需要整理的背包

        // 检查背包类型，并分配需要整理的背包
        switch (topInventory.getType()) {
            case BARREL:
            case CHEST:
            case ENDER_CHEST:
            case SHULKER_BOX: {
                startIndex = 0;
                endIndex = topInventory.getSize();
                sortInventory = topInventory;
                break;
            }
            case CRAFTING: {
                // 如果 topInventory 的 type 是 CRAFTING ，那么说明这个gui是玩家的背包
                startIndex = 9;
                endIndex = 36;
                sortInventory = bottomInventory;
                break;
            }
            default:
                // 其他type一律不处理
                return;
        }

        ArrayList<ItemStack> sortStacks = new ArrayList<>();

        for (int i = startIndex; i < endIndex; i++) {
            ItemStack stack = sortInventory.getItem(i);
            if (stack == null || stack.getType() == Material.AIR) {
                continue;
            }

            mergeAddItem(
                    sortStacks,
                    stack,
                    // 背包或物品类型允许的最大堆叠
                    Math.min(stack.getMaxStackSize(), sortInventory.getMaxStackSize())
            );
        }

        sortStacks.sort(ItemSorter.instance);

        // 把排好序之后的物品set进Inventory里
        for (int i = 0; i < endIndex - startIndex; i++) {
            ItemStack stack = null;
            if (i < sortStacks.size()) {
                stack = sortStacks.get(i);
            }
            sortInventory.setItem(i + startIndex, stack);
        }

        if (notifiedPlayer.add(uuid)) {
            // 第一次整理成功时给出提示
            human.sendMessage("§a==================================================");
            human.sendMessage("§e[五彩方块] wow! 你刚刚完成了一次背包整理工作!");
            human.sendMessage("§e[五彩方块] 在五彩方块服务器, 只需要双击背包外空白区域!");
            human.sendMessage("§e[五彩方块] 即可快速整理任何容器内的物品!");
            human.sendMessage("§a==================================================");
        }
        if (human instanceof Player) {
            Player player = (Player) human;
            Bukkit.getScheduler().runTaskLater(EssentialsPlugin.getInstance(), player::updateInventory, 1);
        }
    }

    /**
     * 添加一个物品到集合中
     * <p>
     * 若集合中已有相同的物品，则尝试先合并物品
     *
     * @param stacks       要添加物品的集合
     * @param addItem      要添加的物品
     * @param maxStackSize 物品允许的最大堆叠
     */
    private void mergeAddItem(Collection<ItemStack> stacks, ItemStack addItem, int maxStackSize) {
        // 如果最大堆叠数量为 1 则直接添加，不判断合并
        // 可以节省一些 CPU 计算性能
        if (maxStackSize <= 1) {
            stacks.add(addItem);
            return;
        }
        for (ItemStack inventoryItem : stacks) {
            int inventoryItemAmount = inventoryItem.getAmount();

            if (inventoryItemAmount >= maxStackSize) {
                // 如果该物品已达到最大堆叠则无视它
                continue;
            }

            if (!inventoryItem.isSimilar(addItem)) {
                // 如果该物品和 addItem 不匹配则无视它
                continue;
            }
            int needAmount = maxStackSize - inventoryItemAmount; //还差多少物品填满
            int addItemAmount = addItem.getAmount();

            if (addItemAmount <= needAmount) {
                inventoryItem.setAmount(inventoryItemAmount + addItemAmount);
                return;
            } else {
                inventoryItem.setAmount(maxStackSize);
                addItem.setAmount(addItemAmount - needAmount);
            }
        }

        stacks.add(addItem);
    }
}
