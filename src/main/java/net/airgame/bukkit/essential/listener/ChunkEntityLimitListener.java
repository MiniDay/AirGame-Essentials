package net.airgame.bukkit.essential.listener;

import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ChunkEntityLimitListener implements Listener {
    private final int animals;
    private final int monsters;

    public ChunkEntityLimitListener(ConfigurationSection config) {
        if (config == null) {
            animals = 15;
            monsters = 40;
            return;
        }
        animals = config.getInt("animals");
        monsters = config.getInt("monsters");
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntitySpawn(EntitySpawnEvent event) {
        switch (event.getEntity().getType()) {
            case WITHER:
            case ENDER_DRAGON:
            case DROPPED_ITEM:
                return;
        }
        int limit = -1;
        if (event.getEntity() instanceof Animals) {
            limit = animals;
        } else if (event.getEntity() instanceof Monster) {
            limit = monsters;
        }
        if (limit < 0) {
            return;
        }
        Chunk chunk = event.getLocation().getChunk();
        int livingEntities = 0;
        for (Entity entity : chunk.getEntities()) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }
            livingEntities++;
        }
        if (livingEntities <= limit) {
            return;
        }
        event.setCancelled(true);
    }
}
