package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@CommandExecutor(
        name = "hat",
        permission = "essentials.hat"
)
@SuppressWarnings("unused")
public class HatCommand {
    @Command
    public void hat(@Sender Player player) {
        PlayerInventory inventory = player.getInventory();

        ItemStack hand = inventory.getItemInMainHand();
        ItemStack helmet = inventory.getHelmet();

        inventory.setHelmet(hand);
        inventory.setItemInMainHand(helmet);

        player.sendMessage("已将手持物品戴在头上.");
    }
}
