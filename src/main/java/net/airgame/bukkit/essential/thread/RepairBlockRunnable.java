package net.airgame.bukkit.essential.thread;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class RepairBlockRunnable extends BukkitRunnable {
    private final HashMap<Location, Material> blockMap;
    private final HashMap<Location, BlockData> blockData;
    private final LinkedList<Location> locations;

    public RepairBlockRunnable(HashMap<Location, Material> blockMap, HashMap<Location, BlockData> blockData) {
        this.blockMap = blockMap;
        this.blockData = blockData;
        locations = new LinkedList<>(blockMap.keySet());
        locations.sort(Comparator.comparingInt(Location::getBlockY).thenComparingInt(Location::getBlockX).thenComparingInt(Location::getBlockZ));
    }

    @Override
    public void run() {
        if (locations.isEmpty()) {
            cancel();
            return;
        }
        Location location = locations.removeFirst();
        Block block = location.getBlock();
        switch (block.getType()) {
            case AIR:
            case WATER:
            case LAVA:
            case FIRE:
                break;
            default:
                return;
        }
        block.setType(blockMap.get(location));
        BlockData data = blockData.get(location);
        if (data != null) {
            block.setBlockData(data);
        }
    }
}
