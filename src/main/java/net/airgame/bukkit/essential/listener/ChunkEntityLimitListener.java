package net.airgame.bukkit.essential.listener;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ChunkEntityLimitListener implements Listener {
    private final int chunkLimit;

    public ChunkEntityLimitListener(int chunkLimit) {
        this.chunkLimit = chunkLimit;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntitySpawn(EntitySpawnEvent event) {
        switch (event.getEntity().getType()) {
            case WITHER:
            case ENDER_DRAGON:
            case DROPPED_ITEM:
                return;
        }
        Chunk chunk = event.getLocation().getChunk();
        if (chunk.getEntities().length >= chunkLimit) {
            event.setCancelled(true);
        }
    }
}
