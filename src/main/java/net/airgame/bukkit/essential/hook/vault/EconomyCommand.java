package net.airgame.bukkit.essential.hook.vault;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import net.airgame.bukkit.api.util.api.EconomyAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "economy",
        aliases = "eco"
)
@SuppressWarnings("unused")
public class EconomyCommand {
    @Command(subName = "take", permission = "essentials.economy.take")
    public void takeMoney(@Sender Player player, double money) {
        EconomyAPI.takeMoney(player, money);
        player.sendMessage(String.format(
                "成功从玩家 %s 的账户扣除 %.2f 金币, 当前玩家账户余额为: %.2f",
                player.getName(),
                money,
                EconomyAPI.seeMoney(player)
        ));
    }

    @Command(subName = "take", permission = "essentials.economy.take")
    public void takeMoney(@Sender CommandSender sender, OfflinePlayer player, double money) {
        EconomyAPI.takeMoney(player, money);
        Player onlinePlayer = player.getPlayer();
        sender.sendMessage(String.format(
                "成功从玩家 %s 的账户扣除 %.2f 金币, 当前玩家账户余额为: %.2f",
                player.getName(),
                money,
                EconomyAPI.seeMoney(player)
        ));
        if (onlinePlayer == null) {
            return;
        }
        onlinePlayer.sendMessage(String.format(
                "已被扣除 %.2f 金币, 当前账户余额为: %.2f",
                money,
                EconomyAPI.seeMoney(player)
        ));
    }

    @Command(subName = "set", permission = "essentials.economy.set")
    public void setMoney(@Sender Player player, double money) {
        EconomyAPI.setMoney(player, money);
        player.sendMessage(String.format(
                "已设置玩家 %s 的账户余额为: %.2f",
                player.getName(),
                money
        ));
    }

    @Command(subName = "set", permission = "essentials.economy.set")
    public void setMoney(@Sender CommandSender sender, OfflinePlayer player, double money) {
        EconomyAPI.setMoney(player, money);
        Player onlinePlayer = player.getPlayer();
        sender.sendMessage(String.format(
                "已设置玩家 %s 的账户余额为: %.2f",
                player.getName(),
                money
        ));
        if (onlinePlayer == null) {
            return;
        }
        onlinePlayer.sendMessage(String.format("当前账户余额已被设置为: %.2f", money));
    }

    @CommandExecutor(
            name = "give",
            permission = "essentials.economy.give"
    )
    public static class Give {
        @Command
        public void giveMoney(@Sender Player player, double money) {
            EconomyAPI.giveMoney(player, money);
            player.sendMessage(String.format(
                    "成功给玩家 %s 账户添加 %.2f 金币, 当前玩家账户余额为: %.2f",
                    player.getName(),
                    money,
                    EconomyAPI.seeMoney(player)
            ));
        }

        @Command
        public void giveMoney(@Sender CommandSender sender, OfflinePlayer player, double money) {
            EconomyAPI.giveMoney(player, money);
            Player onlinePlayer = player.getPlayer();
            sender.sendMessage(String.format(
                    "成功给玩家 %s 账户添加 %.2f 金币, 当前玩家账户余额为: %.2f",
                    player.getName(),
                    money,
                    EconomyAPI.seeMoney(player)
            ));
            if (onlinePlayer == null) {
                return;
            }
            onlinePlayer.sendMessage(String.format(
                    "收到 %.2f 金币, 当前账户余额为: %.2f",
                    money,
                    EconomyAPI.seeMoney(player)
            ));
        }
    }
}
