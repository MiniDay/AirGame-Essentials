package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
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

        sender.sendMessage("服务器性能报告: ");
        sender.sendMessage(String.format("  服务器最大可分配内存: %.2f MB", runtime.maxMemory() / 1024.0 / 1024.0));
        sender.sendMessage(String.format("  服务器已分配内存: %.2f MB", runtime.totalMemory() / 1024.0 / 1024.0));
        sender.sendMessage(String.format("  服务器空闲内存: %.2f MB", runtime.freeMemory() / 1024.0 / 1024.0));
        sender.sendMessage(String.format("  服务器已使用内存: %.2f MB", (runtime.totalMemory() - runtime.freeMemory()) / 1024.0 / 1024.0));
        sender.sendMessage("世界列表: ");
        for (World world : Bukkit.getWorlds()) {
            Chunk[] chunks = world.getLoadedChunks();
            int tiles = 0;
            for (Chunk chunk : chunks) {
                tiles += chunk.getTileEntities().length;
            }
            sender.sendMessage(String.format(
                    "  %s: %d 个区块 %d 实体(%d 活动实体) %d tiles",
                    world.getName(),
                    chunks.length,
                    world.getEntities().size(),
                    world.getLivingEntities().size(),
                    tiles
            ));
        }

    }
}

