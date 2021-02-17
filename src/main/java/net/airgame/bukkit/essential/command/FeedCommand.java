package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "feed",
        aliases = "eat",
        permission = "essentials.feed"
)
@SuppressWarnings("unused")
public class FeedCommand {

    @Command
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
