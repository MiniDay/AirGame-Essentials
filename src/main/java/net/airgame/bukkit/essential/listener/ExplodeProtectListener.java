package net.airgame.bukkit.essential.listener;

import net.airgame.bukkit.essential.EssentialsPlugin;
import net.airgame.bukkit.essential.thread.RepairBlockRunnable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.EnderChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.HashMap;
import java.util.List;

public class ExplodeProtectListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();
        blocks.removeIf(this::checkExplodeProtect);
        HashMap<Location, Material> blockMap = new HashMap<>();
        HashMap<Location, BlockData> blockData = new HashMap<>();
        for (Block block : blocks) {
            // 由于TNT会连锁爆炸, 所以我们不恢复TNT方块
            if (block.getType() == Material.TNT) {
                continue;
            }
            blockMap.put(block.getLocation(), block.getType());
            blockData.put(block.getLocation(), block.getBlockData());
        }
        // 直接清理掉破坏列表，这样将不会掉落任何物品
        // 并手动将这些方块set为空气
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
        blocks.clear();

        new RepairBlockRunnable(blockMap, blockData).runTaskTimer(EssentialsPlugin.getInstance(), 60, 5);
    }

    /**
     * 检查这个方块是否依附在其他方块上
     *
     * @param block 要检查的方块
     * @return true代表若依附的方块被破坏了，则传入的block也会掉落
     */
    private boolean isDrop(Block block) {
        Material type = block.getType();
        switch (type) {
            case REDSTONE:
            case REPEATER:
            case COMPARATOR:
            case DRAGON_HEAD:
            case SCAFFOLDING:
                return true;
        }
        String name = type.name();
        if (name.contains("_TORCH")) {
            return true;
        }
        if (name.contains("_DOOR")) {
            return true;
        }
        if (name.contains("_PLATE")) {
            return true;
        }
        if (name.contains("_TRAPDOOR")) {
            return true;
        }
        if (name.contains("_BUTTON")) {
            return true;
        }
        if (name.contains("_SIGN")) {
            return true;
        }
        if (name.contains("_SKULL")) {
            return true;
        }
        return name.contains("_RAIL");
    }

    /**
     * 检查这个方块是否需要爆炸保护
     *
     * @param block 检查的方块
     * @return true代表爆炸不破坏该方块
     */
    private boolean checkExplodeProtect(Block block) {
        if (isDrop(block)) {
            return true;
        }
        Block front = block.getRelative(1, 0, 0);
        if (isDrop(front)) {
            return true;
        }
        Block behind = block.getRelative(-1, 0, 0);
        if (isDrop(behind)) {
            return true;
        }
        Block left = block.getRelative(0, 0, 1);
        if (isDrop(left)) {
            return true;
        }
        Block right = block.getRelative(0, 0, -1);
        if (isDrop(right)) {
            return true;
        }
        Block top = block.getRelative(0, 1, 0);
        if (isDrop(top)) {
            return true;
        }
        Block under = block.getRelative(0, -1, 0);
        if (isDrop(under)) {
            return true;
        }
        if (block.getBlockData() instanceof Chest) {
            return true;
        }
        if (block.getBlockData() instanceof EnderChest) {
            return true;
        }
        return block.getBlockData() instanceof Container;
    }
}
