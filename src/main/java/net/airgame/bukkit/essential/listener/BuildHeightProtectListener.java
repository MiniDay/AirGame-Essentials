package net.airgame.bukkit.essential.listener;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;

public class BuildHeightProtectListener implements Listener {
    private final HashMap<String, Integer> buildHeightLimit;

    public BuildHeightProtectListener(ConfigurationSection config) {
        buildHeightLimit = new HashMap<>();
        if (config == null) {
            return;
        }
        ConfigurationSection buildHeightLimitConfig = config.getConfigurationSection("worlds");
        if (buildHeightLimitConfig != null) {
            for (String key : buildHeightLimitConfig.getKeys(false)) {
                buildHeightLimit.put(key, buildHeightLimitConfig.getInt(key));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("essentials.build.height.limit")) {
            return;
        }
        Block block = event.getBlock();
        Integer buildHeight = buildHeightLimit.get(block.getWorld().getName());
        if (buildHeight == null) {
            return;
        }
        if (block.getY() < buildHeight) {
            return;
        }
        event.setCancelled(true);
        player.sendMessage(String.format("这个世界的最大建筑高度为 %d 格!", buildHeight));
    }
}
