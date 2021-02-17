package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandExecutor(
        name = "experience",
        aliases = "exp"
)
@SuppressWarnings("unused")
public class ExperienceCommand {

    @Command(permission = "essentials.exp.see")
    public void exp(@Sender Player player) {
        player.sendMessage(String.format(
                "玩家 %s 当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                player.getName(),
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(subName = "see", permission = "essentials.exp.see")
    public void see(@Sender Player player) {
        player.sendMessage(String.format(
                "玩家 %s 当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                player.getName(),
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(subName = "see", permission = "essentials.exp.see.other")
    public void see(@Sender CommandSender sender, Player player) {
        sender.sendMessage(String.format(
                "玩家 %s 当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                player.getName(),
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(subName = "give", permission = "essentials.exp.give")
    public void give(@Sender Player player, int exp) {
        player.setTotalExperience(player.getTotalExperience() + exp);
        player.sendMessage(String.format(
                "已成功给予玩家 %d 点经验值. 玩家 %s 当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                exp,
                player.getName(),
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(subName = "give", permission = "essentials.exp.give")
    public void give(@Sender CommandSender sender, Player player, int exp) {
        player.setTotalExperience(player.getTotalExperience() + exp);
        sender.sendMessage(String.format(
                "已成功给予玩家 %d 点经验值. 玩家 %s 当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                exp,
                player.getName(),
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(subName = "take", permission = "essentials.exp.take")
    public void take(@Sender Player player, int exp) {
        player.setTotalExperience(player.getTotalExperience() - exp);
        player.sendMessage(String.format(
                "已成功扣除玩家 %d 点经验值. 玩家 %s 当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                exp,
                player.getName(),
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(subName = "take", permission = "essentials.exp.take")
    public void take(@Sender CommandSender sender, Player player, int exp) {
        player.setTotalExperience(player.getTotalExperience() - exp);
        sender.sendMessage(String.format(
                "已成功扣除玩家 %d 点经验值. 玩家 %s 当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                exp,
                player.getName(),
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(subName = "set", permission = "essentials.exp.set")
    public void set(@Sender Player player, int exp) {
        player.setTotalExperience(exp);
        player.sendMessage(String.format(
                "已成功设置玩家 %s 经验值为 %d 点. 玩家当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                player.getName(),
                exp,
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(subName = "set", permission = "essentials.exp.set")
    public void set(@Sender CommandSender sender, Player player, int exp) {
        player.setTotalExperience(exp);
        sender.sendMessage(String.format(
                "已成功设置玩家 %s 经验值为 %d 点. 玩家当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                sender.getName(),
                exp,
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }

    @Command(permission = "essentials.exp.see.other")
    public void exp(@Sender CommandSender sender, Player player) {
        sender.sendMessage(String.format(
                "玩家 %s 当前等级为 %d，总共经验值为 %d. 当前等级经验值为 %d 点，还需要 %d 点升至下一级.",
                player.getName(),
                player.getLevel(),
                player.getTotalExperience(),
                (int) (player.getExpToLevel() * player.getExp()),
                (int) (player.getExpToLevel() * (1 - player.getExp()))
        ));
    }
}
