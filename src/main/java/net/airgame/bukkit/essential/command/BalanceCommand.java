package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import net.airgame.bukkit.api.util.api.EconomyAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "balance",
        aliases = {"bal", "money"},
        permission = "essentials.balance"
)
@SuppressWarnings("unused")
public class BalanceCommand {

    @Command
    public void balance(@Sender Player player) {
        player.sendMessage(String.format("%s 的余额为: %.2f", player.getName(), EconomyAPI.seeMoney(player)));
    }

    @Command
    public void balance(@Sender CommandSender sender, Player player) {
        sender.sendMessage(String.format("%s 的余额为: %.2f", player.getName(), EconomyAPI.seeMoney(player)));
    }

}