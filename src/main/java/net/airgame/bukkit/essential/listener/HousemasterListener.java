package net.airgame.bukkit.essential.listener;

import net.airgame.bukkit.api.util.StringUtils;
import net.airgame.bukkit.essential.core.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.ArrayList;

/**
 * 宿管监听器
 * <p>
 * 当有玩家睡觉时广播未入睡玩家的名字
 */
public final class HousemasterListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage(
                Message.playerBedEnter.toString()
                        .replace("%player%", player.getName())
        );

        ArrayList<Player> notSleepPlayers = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.isSleepingIgnored()) {
                continue;
            }
            if (onlinePlayer.isSleeping()) {
                continue;
            }
            if (onlinePlayer == player) {
                continue;
            }
            if (onlinePlayer.getGameMode() == GameMode.SPECTATOR) {
                continue;
            }
            notSleepPlayers.add(onlinePlayer);
        }
        if (notSleepPlayers.size() < 1) {
            Bukkit.broadcastMessage(Message.allPlayerSleeping.toString());
            return;
        }
        String s = StringUtils.join(notSleepPlayers.stream().map(Player::getName).iterator(), ", ");
        for (Player worldPlayer : player.getWorld().getPlayers()) {
            worldPlayer.sendMessage(Message.notAllPlayerSleeping.toString().replace("%not_sleep_players%", s));
        }
    }
}
