package net.airgame.bukkit.essential.command;

import net.airgame.bukkit.api.annotation.Command;
import net.airgame.bukkit.api.annotation.CommandExecutor;
import net.airgame.bukkit.api.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

@CommandExecutor(
        name = "listEntity",
        aliases = {"listEntities", "lEntity", "lEntities", "entities"},
        permission = "essentials.listEntities"
)
@SuppressWarnings("unused")
public class ListEntityCommand {
    @Command
    public void listEntity(@Sender CommandSender sender) {
        for (World world : Bukkit.getWorlds()) {
            HashMap<EntityType, Integer> entityCount = new HashMap<>();
            for (Entity entity : world.getEntities()) {
                int i = entityCount.getOrDefault(entity.getType(), 0) + 1;
                entityCount.put(entity.getType(), i);
            }
            sender.sendMessage("世界 " + world.getName() + " 的实体列表: ");
            for (Map.Entry<EntityType, Integer> entry : entityCount.entrySet()) {
                sender.sendMessage("  " + entry.getKey().name() + " 数量: " + entry.getValue());
            }
        }
    }

    @Command
    public void listEntity(@Sender CommandSender sender, World world) {
        HashMap<EntityType, Integer> entityCount = new HashMap<>();
        for (Entity entity : world.getEntities()) {
            int i = entityCount.getOrDefault(entity.getType(), 0) + 1;
            entityCount.put(entity.getType(), i);
        }
        sender.sendMessage("世界 " + world.getName() + " 的实体列表: ");
        for (Map.Entry<EntityType, Integer> entry : entityCount.entrySet()) {
            sender.sendMessage("  " + entry.getKey().name() + " 数量: " + entry.getValue());
        }
    }
}
