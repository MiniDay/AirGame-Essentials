package net.airgame.bukkit.essential.listener;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.UUID;

public class ChainBreakListener implements Listener {
    private final HashSet<BlockBreakEvent> ignoreEvents;
    private final HashSet<UUID> notifiedPlayer;

    public ChainBreakListener() {
        ignoreEvents = new HashSet<>();
        notifiedPlayer = new HashSet<>();
    }

    private void searchChainBlock(Block block, HashSet<Block> chainBlocks, HashSet<Block> searched, ChainType type) {
        if (searched.size() > 500) {
            return;
        }
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }
                    Block nextBlock = block.getRelative(x, y, z);
                    if (searched.contains(nextBlock)) {
                        continue;
                    }
                    searched.add(nextBlock);

                    if (chainBlocks.contains(nextBlock)) {
                        continue;
                    }
                    if (nextBlock.getType() == block.getType()) {
                        chainBlocks.add(nextBlock);
                        searchChainBlock(nextBlock, chainBlocks, searched, type);
                        continue;
                    }
                    switch (type) {
                        case WOOD: {
                            if (nextBlock.getType().name().contains("_LEAVES")) {
                                if (block.getType().name().contains("_LOG")) {
                                    chainBlocks.add(nextBlock);
                                }
                                searchChainBlock(nextBlock, chainBlocks, searched, type);
                                continue;
                            }
                            if (nextBlock.getType().name().contains("_LOG")) {
                                chainBlocks.add(nextBlock);
                                searchChainBlock(nextBlock, chainBlocks, searched, type);
                            }
                            break;
                        }
                        case MUSHROOM: {
                            if (nextBlock.getType().name().contains("MUSHROOM")) {
                                chainBlocks.add(nextBlock);
                                searchChainBlock(nextBlock, chainBlocks, searched, type);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private ChainType getChainType(Block block, ItemStack stack) {
        if (block == null) {
            return null;
        }
        if (stack == null) {
            return null;
        }
        String itemType = stack.getType().name();
        String blockName = block.getType().name();

        if (itemType.contains("_AXE")) {
            if (blockName.contains("_LOG")) {
                return ChainType.WOOD;
            }
            if (blockName.contains("MUSHROOM")) {
                return ChainType.MUSHROOM;
            }
        }
        if (blockName.contains("_ORE")) {
            if (itemType.contains("_PICKAXE")) {
                return ChainType.ORE;
            }
        }
        return null;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onChainBreak(BlockBreakEvent event) {
        if (ignoreEvents.remove(event)) {
            // 如果这个事件对象是需要被忽略的则直接不处理
            return;
        }
        Player player = event.getPlayer();

        if (player.isSneaking()) {
            // 如果玩家在潜行状态则不进行连锁破坏
            return;
        }

        ItemStack stack = player.getInventory().getItemInMainHand();
        Damageable damageable = (Damageable) stack.getItemMeta();
        if (damageable == null) {
            return;
        }

        Block block = event.getBlock();
        ChainType type = getChainType(block, stack);
        if (type == null) {
            return;
        }
        HashSet<Block> breakBlocks = new HashSet<>();
        searchChainBlock(block, breakBlocks, new HashSet<>(), type);

        int cancelledCount = 0;
        for (Block breakBlock : breakBlocks) {
            // 我们需要重新调用一次BlockBreakEvent事件
            // 这样可以让其他插件检查一遍玩家是否允许破坏这个方块
            // 不然玩家可能可以利用连锁破坏来拆毁其他玩家的家
            BlockBreakEvent blockBreakEvent = new BlockBreakEvent(breakBlock, player);
            // 同时我们需要把这个BlockBreakEvent添加到忽略事件中
            // 不然onChainBreak这个事件处理器会再一次尝试“连锁破坏”那个方块
            // 最终造成无限递归让服务器假死
            ignoreEvents.add(blockBreakEvent);
            Bukkit.getPluginManager().callEvent(blockBreakEvent);
            if (blockBreakEvent.isCancelled()) {
                cancelledCount++;
                continue;
            }
            breakBlock.breakNaturally(stack);
        }

        int damage = breakBlocks.size() - cancelledCount;
        if (damage == 0) {
            return;
        }

        int level = stack.getEnchantmentLevel(Enchantment.DURABILITY);
        damage = damage / (level + 1);
        int itemDurability = damageable.getDamage() + damage;
        if (itemDurability > stack.getType().getMaxDurability()) {
            player.getInventory().setItemInMainHand(null);
            return;
        }
        damageable.setDamage(itemDurability);
        stack.setItemMeta((ItemMeta) damageable);

        UUID uuid = player.getUniqueId();
        if (notifiedPlayer.contains(uuid)) {
            return;
        }
        notifiedPlayer.add(uuid);
        player.sendMessage("§e============================================================================");
        player.sendMessage("§a[连锁破坏] 你触发了连锁破坏!");
        player.sendMessage("§a[连锁破坏] 五彩方块服务器的连锁破坏功能允许你轻松拆除 树木、矿物、蘑菇树 等方块!");
        player.sendMessage("§a[连锁破坏] 当你使用对应的工具破坏上述方块时, 连锁破坏将会自动生效!");
        player.sendMessage("§a[连锁破坏] 如果想要禁用该功能, 只需要在潜行状态破坏方块即可!");
        player.sendMessage("§e============================================================================");
    }

    public enum ChainType {
        WOOD,
        MUSHROOM,
        ORE
    }

}
