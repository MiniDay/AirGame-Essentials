package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Objects;

@CommandExecutor(
        name = "seen",
        permission = "essentials.seen"
)
@SuppressWarnings("unused")
public class SeenCommand {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Command
    public void seen(@Sender CommandSender sender, OfflinePlayer player) {
        Player onlinePlayer = player.getPlayer();
        sender.sendMessage("uuid: " + player.getUniqueId());
        if (onlinePlayer == null) {
            sender.sendMessage("最后一次在线时间: " + format.format(player.getLastPlayed()));
        } else {
            sender.sendMessage("IP地址: " + onlinePlayer.getAddress());
            World world = onlinePlayer.getWorld();
            sender.sendMessage("当前位置: " + String.format("%s;%.2f;%.2f;%.2f",
                    world.getName(),
                    onlinePlayer.getLocation().getX(),
                    onlinePlayer.getLocation().getY(),
                    onlinePlayer.getLocation().getZ()
            ));
            sender.sendMessage(String.format("当前生命值: %d / %d", (int) onlinePlayer.getHealth(), (int) (Objects.requireNonNull(onlinePlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue())));
            sender.sendMessage(String.format("当前饱食度: %d / %d", onlinePlayer.getFoodLevel(), 20));
            sender.sendMessage("当前等级: " + onlinePlayer.getLevel());
            sender.sendMessage(String.format("当前氧气值: %d / %d", onlinePlayer.getRemainingAir(), onlinePlayer.getMaximumAir()));
        }
    }

}
