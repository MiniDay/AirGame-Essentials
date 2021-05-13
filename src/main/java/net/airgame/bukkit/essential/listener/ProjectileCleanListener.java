package net.airgame.bukkit.essential.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 投射物清理
 */
public class ProjectileCleanListener extends BukkitRunnable implements Listener {
    private final int clearDelay;
    private final HashMap<UUID, Long> projectileSpawnTime;

    public ProjectileCleanListener(int clearDelay) {
        this.clearDelay = clearDelay;
        projectileSpawnTime = new HashMap<>();
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        for (Map.Entry<UUID, Long> entry : new HashMap<>(projectileSpawnTime).entrySet()) {
            if (now - entry.getValue() < clearDelay) {
                continue;
            }
            UUID uuid = entry.getKey();
            projectileSpawnTime.remove(uuid);
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) {
                continue;
            }
            entity.remove();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        projectileSpawnTime.put(event.getEntity().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onProjectileHit(ProjectileHitEvent event) {
        projectileSpawnTime.remove(event.getEntity().getUniqueId());
    }
}
