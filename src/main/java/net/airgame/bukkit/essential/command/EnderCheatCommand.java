package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "enderChest",
        aliases = {"eChest",  "eenderChest", "enderSee", "ec", "eec"}
)
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class EnderCheatCommand {

    @Command(permission = "essentials.enderChest")
    public void enderchest(@Sender Player player) {
        player.openInventory(player.getEnderChest());
    }

    @Command(permission = "essentials.enderChest.other")
    public void enderchest(@Sender Player player1, Player player2) {
        player1.openInventory(player2.getEnderChest());
    }
}
