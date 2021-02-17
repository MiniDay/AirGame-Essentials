package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "fly",
        permission = "essentials.fly"
)
@SuppressWarnings("unused")
public class FlyCommand {

    @Command
    public void fly(@Sender Player player) {
        player.setAllowFlight(!player.getAllowFlight());
        player.sendMessage(String.format(
                player.getAllowFlight() ? "已开启玩家 %s 的飞行模式." : "已关闭玩家 %s 的飞行模式.",
                player.getName()
        ));
    }

    @Command(permission = "essentials.fly.other")
    public void fly(@Sender CommandSender sender, Player player) {
        player.setAllowFlight(!player.getAllowFlight());
        sender.sendMessage(String.format(
                player.getAllowFlight() ? "已开启玩家 %s 的飞行模式." : "已关闭玩家 %s 的飞行模式.",
                player.getName()
        ));
    }

    @Command
    public void fly(@Sender Player player, boolean b) {
        player.setAllowFlight(b);
        player.sendMessage(String.format(
                player.getAllowFlight() ? "已开启玩家 %s 的飞行模式." : "已关闭玩家 %s 的飞行模式.",
                player.getName()
        ));
    }

    @Command(permission = "essentials.fly.other")
    public void fly(@Sender CommandSender sender, Player player, boolean b) {
        player.setAllowFlight(b);
        sender.sendMessage(String.format(
                player.getAllowFlight() ? "已开启玩家 %s 的飞行模式." : "已关闭玩家 %s 的飞行模式.",
                player.getName()
        ));
    }
}
