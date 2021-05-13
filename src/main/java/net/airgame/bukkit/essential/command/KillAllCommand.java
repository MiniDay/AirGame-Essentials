package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "killAll",
        permission = "essentials.killall"
)
@SuppressWarnings("unused")
public class KillAllCommand {
    @Command
    public void killall(@Sender Player player) {
        killall(player, player.getWorld());
    }

    @Command
    public void killall(@Sender Player player, EntityType entityType) {
        killall(player, player.getWorld(), entityType);
    }

    @Command
    public void killall(@Sender CommandSender sender, World world) {
        int i = 0;
        for (Entity entity : world.getEntities()) {
            if (entity.getCustomName() != null) {
                continue;
            }
            entity.remove();
            i++;
        }
        sender.sendMessage(String.format("§a成功移除所有世界中的 %d 个实体.", i));
    }

    @Command
    public void killall(@Sender CommandSender sender, EntityType entityType) {
        int i = 0;
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() != entityType) {
                    continue;
                }
                if (entity.getCustomName() != null) {
                    continue;
                }
                entity.remove();
                i++;
            }
        }
        sender.sendMessage(String.format("§a成功移除所有世界中的 %d 个 %s.", i, entityType.name()));
    }

    @Command
    public void killall(@Sender CommandSender sender, World world, EntityType entityType) {
        int i = 0;
        for (Entity entity : world.getEntities()) {
            if (entity.getType() != entityType) {
                continue;
            }
            if (entity.getCustomName() != null) {
                continue;
            }
            entity.remove();
            i++;
        }
        sender.sendMessage(String.format("§a成功移除世界 %s 中的 %d 个 %s.", world.getName(), i, entityType.name()));
    }
}
