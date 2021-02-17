package net.airgame.bukkit.essential.hook.vault;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import net.airgame.bukkit.api.util.api.EconomyAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


@CommandExecutor(
        name = "pay",
        permission = "essentials.pay"
)
@SuppressWarnings("unused")
public class PayCommand {
    @Command
    public void pay(@Sender Player player1, OfflinePlayer player2, double money) {
        if (!EconomyAPI.takeMoney(player1, money)) {
            player1.sendMessage("你的账户余额不足!");
            return;
        }
        EconomyAPI.giveMoney(player2, money);
        Player player = player2.getPlayer();
        if (player != null) {
            player.sendMessage(String.format(
                    "玩家 %s 向你转账 %.2f 金币.",
                    player1.getName(),
                    money
            ));
        }
        player1.sendMessage(String.format("已向玩家 %s 转账 %.2f 金币.", player2.getName(), money));
    }
}
