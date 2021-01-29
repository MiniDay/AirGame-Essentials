package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import net.airgame.bukkit.essential.EssentialsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;


@CommandExecutor(
        name = "performance",
        aliases = {"gc", "lag"},
        permission = "essentials.gc"
)
@SuppressWarnings("unused")
public class PerformanceCommand {
    @Command
    public void performance(@Sender CommandSender sender) {
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
            sender.sendMessage(String.format(
                    "  %s: %,d 区块 %,d 实体(%,d 活动 | %,d 玩家)  %,d tiles 共计运行: %,d ticks(%,.2f 天)",
                    world.getName(),
                    chunks.length,
                    world.getEntities().size(),
                    world.getLivingEntities().size(),
                    world.getPlayers().size(),
                    tiles,
                    world.getFullTime(),
                    world.getFullTime() / 20.0 / 86400.0
            ));
        }
        long time = (System.currentTimeMillis() - EssentialsPlugin.getInstance().getServerStartTime()) / 1000;
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

