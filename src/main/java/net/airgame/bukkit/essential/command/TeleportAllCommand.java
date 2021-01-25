package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandExecutor(
        name = "teleportAll",
        aliases = {"tpAll"},
        permission = "essentials.tpAll"
)
@SuppressWarnings("unused")
public class TeleportAllCommand {
    @Command
    public void tpAll(@Sender Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (player == onlinePlayer) {
                continue;
            }
            onlinePlayer.teleport(player, PlayerTeleportEvent.TeleportCause.PLUGIN);
            onlinePlayer.sendMessage(String.format("%s 已将你传送至 %s 的位置.", player.getName(), player.getName()));
        }
    }

    @Command
    public void tpAll(@Sender CommandSender sender, Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (player == onlinePlayer) {
                continue;
            }
            onlinePlayer.teleport(player, PlayerTeleportEvent.TeleportCause.PLUGIN);
            onlinePlayer.sendMessage(String.format("%s 已将你传送至 %s 的位置.", sender.getName(), player.getName()));
        }
    }
}
