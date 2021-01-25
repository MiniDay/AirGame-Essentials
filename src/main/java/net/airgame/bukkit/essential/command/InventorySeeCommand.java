package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "inventorySee",
        aliases = {"invsee"},
        permission = "essentials.invsee"
)
@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class InventorySeeCommand {

    @Command
    public void see(@Sender Player player1, Player player2) {
        player1.openInventory(player2.getInventory());
    }
}
