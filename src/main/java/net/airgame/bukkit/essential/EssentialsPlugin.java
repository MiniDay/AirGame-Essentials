package net.airgame.bukkit.essential;

import net.airgame.bukkit.api.command.annotation.CommandScan;
import net.airgame.bukkit.essential.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
@CommandScan("net.airgame.bukkit.essential.command")
public final class EssentialsPlugin extends JavaPlugin {
    private static EssentialsPlugin instance;
    private long serverStartTime;

    public static EssentialsPlugin getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();
        if (config.getBoolean("chainBreak")) {
            Bukkit.getPluginManager().registerEvents(new ChainBreakListener(), this);
        }
        if (config.getBoolean("housemaster")) {
            Bukkit.getPluginManager().registerEvents(new HousemasterListener(), this);
        }
        if (config.getBoolean("inventoryTweaks")) {
            Bukkit.getPluginManager().registerEvents(new InventoryTweaksListener(), this);
        }
        if (config.getBoolean("buildHeightProtect.enable")) {
            ConfigurationSection section = config.getConfigurationSection("buildHeightProtect");
            Bukkit.getPluginManager().registerEvents(new BuildHeightProtectListener(section), this);
        }
        if (config.getBoolean("explodeProtect")) {
            Bukkit.getPluginManager().registerEvents(new ExplodeProtectListener(), this);
        }
        Bukkit.getScheduler().runTask(this, () -> serverStartTime = System.currentTimeMillis());
    }

    public long getServerStartTime() {
        return serverStartTime;
    }

}
