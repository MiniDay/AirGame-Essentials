package net.airgame.bukkit.essential;

import net.airgame.bukkit.api.annotation.CommandScan;
import net.airgame.bukkit.api.util.LogUtils;
import net.airgame.bukkit.essential.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
@CommandScan("net.airgame.bukkit.essential.command")
public final class EssentialsPlugin extends JavaPlugin {
    private static EssentialsPlugin instance;
    private static LogUtils logUtils;

    public static EssentialsPlugin getInstance() {
        return instance;
    }

    public static LogUtils getLogUtils() {
        return logUtils;
    }

    @Override
    public void onLoad() {
        instance = this;
        logUtils = new LogUtils(this);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();
        if (config.getBoolean("buildHeightProtect.enable")) {
            ConfigurationSection section = config.getConfigurationSection("buildHeightProtect");
            Bukkit.getPluginManager().registerEvents(new BuildHeightProtectListener(section), this);
            logUtils.info("已启用 BuildHeightProtectListener.");
        }
        if (config.getBoolean("chainBreak")) {
            Bukkit.getPluginManager().registerEvents(new ChainBreakListener(), this);
            logUtils.info("已启用 ChainBreakListener.");
        }
        if (config.getBoolean("chunkEntityLimit.enable")) {
            ConfigurationSection limitConfig = config.getConfigurationSection("chunkEntityLimit.limits");
            Bukkit.getPluginManager().registerEvents(new ChunkEntityLimitListener(limitConfig), this);
            logUtils.info("已启用 ChunkEntityLimitListener.");
        }
        if (config.getBoolean("damageReporter")) {
            Bukkit.getPluginManager().registerEvents(new DamageReporterListener(), this);
            logUtils.info("已启用 DamageReporterListener.");
        }
        if (config.getBoolean("explodeProtect")) {
            Bukkit.getPluginManager().registerEvents(new ExplodeProtectListener(), this);
            logUtils.info("已启用 ExplodeProtectListener.");
        }
        if (config.getBoolean("housemaster")) {
            Bukkit.getPluginManager().registerEvents(new HousemasterListener(), this);
            logUtils.info("已启用 HousemasterListener.");
        }
        if (config.getBoolean("inventoryTweaks")) {
            Bukkit.getPluginManager().registerEvents(new InventoryTweaksListener(), this);
            logUtils.info("已启用 InventoryTweaksListener.");
        }
        if (config.getBoolean("projectileClean.enable")) {
            int clearDelay = config.getInt("projectileClean.clearDelay");
            int clearInterval = config.getInt("projectileClean.clearInterval");
            ProjectileCleanListener listener = new ProjectileCleanListener(clearDelay);
            Bukkit.getPluginManager().registerEvents(listener, this);
            listener.runTaskTimer(this, clearInterval, clearInterval);
            logUtils.info("已启用 ProjectileCleanListener.");
        }
        Bukkit.getScheduler().runTask(this, () -> {
            if (System.getProperties().getProperty("BukkitStartTime") == null) {
                System.getProperties().setProperty("BukkitStartTime", String.valueOf(System.currentTimeMillis()));
            }
        });
        logUtils.info("插件启动完成.");
    }

}
