package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;


@CommandExecutor(
        name = "performance",
        aliases = {"gc", "lag"},
        permission = "essentials.gc"
)
@SuppressWarnings("unused")
public class PerformanceCommand {
    @Command
    public void performance(@Sender CommandSender sender) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Runtime runtime = Runtime.getRuntime();

        sender.sendMessage("服务器内存报告: ");
        sender.sendMessage(String.format("  最大可分配内存: %.2f MB", runtime.maxMemory() / 1024.0 / 1024.0));
        sender.sendMessage(String.format("  已分配内存: %.2f MB", runtime.totalMemory() / 1024.0 / 1024.0));
        sender.sendMessage(String.format("  空闲内存: %.2f MB", runtime.freeMemory() / 1024.0 / 1024.0));
        sender.sendMessage(String.format("  已使用内存: %.2f MB", (runtime.totalMemory() - runtime.freeMemory()) / 1024.0 / 1024.0));
        sender.sendMessage("世界列表: ");
        for (World world : Bukkit.getWorlds()) {
            Chunk[] chunks = world.getLoadedChunks();
            int tiles = 0;
            for (Chunk chunk : chunks) {
                tiles += chunk.getTileEntities().length;
            }
            Object handle = world.getClass().getMethod("getHandle").invoke(world);
            long worldFullTime = (long) handle.getClass().getMethod("getTime").invoke(handle);

            sender.sendMessage(String.format(
                    "  %s: %,d 区块 %,d 实体(%,d 活动 | %,d 玩家)  %,d tiles 共计运行: %,d ticks(%,.2f 天)",
                    world.getName(),
                    chunks.length,
                    world.getEntities().size(),
                    world.getLivingEntities().size(),
                    world.getPlayers().size(),
                    tiles,
                    worldFullTime,
                    worldFullTime / 20.0 / 86400.0
            ));
        }
        long time = (System.currentTimeMillis() - Long.parseLong(System.getProperty("BukkitStartTime"))) / 1000;
        sender.sendMessage(String.format(
                "服务器已运行 %d 月 %d 天 %d 小时 %d 分钟 %d 秒",
                time / 2592000,
                time % 2592000 / 86400,
                time % 86400 / 3600,
                time % 3600 / 60,
                time % 60
        ));
    }
}

