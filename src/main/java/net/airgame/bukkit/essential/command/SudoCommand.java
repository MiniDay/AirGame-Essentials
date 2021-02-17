package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import net.airgame.bukkit.api.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "sudo",
        aliases = "su",
        permission = "essentials.sudo"
)
@SuppressWarnings("unused")
public class SudoCommand {
    @Command
    public void sudo(@Sender CommandSender sender, Player player, String[] args) {
        if (sender == player) {
            sender.sendMessage("你不能强制自己执行指令.");
            return;
        }
        String command = StringUtils.join(args, " ");
        Bukkit.dispatchCommand(player, command);
        sender.sendMessage(String.format("强制玩家 %s 执行指令: /%s", player.getName(), command));
    }
}
