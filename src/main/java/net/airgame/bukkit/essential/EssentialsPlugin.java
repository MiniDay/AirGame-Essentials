package net.airgame.bukkit.essential;

import net.airgame.bukkit.api.command.annotation.CommandScan;
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
    private long serverStartTime;

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
            logUtils.info("已启用建筑高度限制器.");
        }
        if (config.getBoolean("chainBreak")) {
            Bukkit.getPluginManager().registerEvents(new ChainBreakListener(), this);
            logUtils.info("已启用连锁破坏模块.");
        }
        if (config.getBoolean("damageReporter")) {
            Bukkit.getPluginManager().registerEvents(new DamageReporterListener(), this);
            logUtils.info("已启用伤害汇报模块.");
        }
        if (config.getBoolean("explodeProtect")) {
            Bukkit.getPluginManager().registerEvents(new ExplodeProtectListener(), this);
            logUtils.info("已启用爆炸保护模块.");
        }
        if (config.getBoolean("housemaster")) {
            Bukkit.getPluginManager().registerEvents(new HousemasterListener(), this);
            logUtils.info("已启用宿管模块.");
        }
        if (config.getBoolean("inventoryTweaks")) {
            Bukkit.getPluginManager().registerEvents(new InventoryTweaksListener(), this);
            logUtils.info("已启用背包整理模块.");
        }
        Bukkit.getScheduler().runTask(this, () -> serverStartTime = System.currentTimeMillis());
        logUtils.info("插件启动完成.");
    }

    public long getServerStartTime() {
        return serverStartTime;
    }

}
