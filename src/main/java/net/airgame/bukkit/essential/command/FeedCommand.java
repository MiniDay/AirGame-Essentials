package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "feed",
        aliases = "eat"
)
@SuppressWarnings("unused")
public class FeedCommand {

    @Command(permission = "essentials.feed")
    public void feed(@Sender Player player) {
        player.setFoodLevel(20);
        player.sendMessage(String.format("已恢复玩家 %s 的饱食度.", player.getName()));
    }

    @Command(permission = "essentials.feed.other")
    public void feed(@Sender CommandSender sender, Player player) {
        player.setFoodLevel(20);
        sender.sendMessage(String.format("已恢复玩家 %s 的饱食度.", player.getName()));
    }
}
