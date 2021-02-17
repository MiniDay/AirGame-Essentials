package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "enderChest",
        aliases = {"eChest", "eenderChest", "enderSee", "ec", "eec"},
        permission = "essentials.enderChest"
)
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class EnderCheatCommand {

    public void enderchest(@Sender Player player) {
        player.openInventory(player.getEnderChest());
    }

    @Command(permission = "essentials.enderChest.other")
    public void enderchest(@Sender Player player1, Player player2) {
        player1.openInventory(player2.getEnderChest());
    }
}
