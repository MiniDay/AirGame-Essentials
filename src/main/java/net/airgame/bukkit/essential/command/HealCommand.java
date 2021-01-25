package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@CommandExecutor(
        name = "heal"
)
@SuppressWarnings("unused")
public class HealCommand {

    @Command(permission = "essentials.heal")
    public void heal(@Sender Player player) {
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        player.sendMessage(String.format("已恢复玩家 %s 的生命值.", player.getName()));
    }

    @Command(permission = "essentials.heal.other")
    public void heal(@Sender CommandSender sender, Player player) {
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        player.sendMessage(String.format("已恢复玩家 %s 的生命值.", player.getName()));
    }
}
