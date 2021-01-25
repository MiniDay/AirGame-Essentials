package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandExecutor(
        name = "tp",
        description = "传送指令",
        aliases = {"teleport", "mtp"},
        usage = "/tp 位置"
)
@SuppressWarnings("unused")
public class TeleportCommand {

    @Command(permission = "essentials.tp.other")
    public void tp(@Sender CommandSender sender, Player player1, Player player2) {
        if (player1 == player2) {
            sender.sendMessage("你不能让玩家传送他自己!");
            return;
        }
        player1.teleport(player2, PlayerTeleportEvent.TeleportCause.PLUGIN);
        player1.sendMessage(String.format("%s 已将你传送至 %s 的位置.", sender.getName(), player2.getName()));
    }

    @Command(permission = "essentials.tp.other")
    public void tp(@Sender CommandSender sender, Player player, Location location) {
        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage(String.format("%s 已将你传送至当前位置.", sender.getName()));
    }

    @Command(permission = "essentials.tp")
    public void tp(@Sender Entity entity, Location location) {
        entity.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        entity.sendMessage(String.format("已传送至位置 %s", location));
    }

    @Command(permission = "essentials.tp")
    public void tp(@Sender Entity entity, Player player) {
        if (entity == player) {
            entity.sendMessage("§c你不能传送你自己!");
            return;
        }
        entity.teleport(player, PlayerTeleportEvent.TeleportCause.PLUGIN);
        entity.sendMessage(String.format("已传送至 %s 的位置.", player.getName()));
    }

    @Command(permission = "essentials.tp")
    public void tp(@Sender Entity entity, World world) {
        entity.teleport(world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        entity.sendMessage(String.format("已传送至世界 %s 的出生点.", world.getName()));
    }

}
