package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.command.annotation.Command;
import net.airgame.bukkit.api.command.annotation.CommandExecutor;
import net.airgame.bukkit.api.command.annotation.Sender;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "gameMode",
        aliases = {"gMode", "mode"}
)
@SuppressWarnings("unused")
public class GameModeCommand {

    @Command(permission = "essentials.gameMode")
    public void gameMode(@Sender Player player, GameMode mode) {
        player.setGameMode(mode);
        player.sendMessage("游戏模式已切换至 " + mode.name());
    }

    @Command(permission = "essentials.gameMode.other")
    public void gameMode(@Sender CommandSender sender, Player player, GameMode mode) {
        player.sendMessage(mode.name());
        player.sendMessage(sender.getName() + " 已将你的游戏模式已切换至 " + mode.name());
    }

}
