package net.airgame.bukkit.essential.listener;

import net.airgame.bukkit.api.util.MessageUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class DamageReporterListener implements Listener {
    @SuppressWarnings("ConstantConditions")
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        Entity damager = event.getDamager();
        if (damager instanceof Projectile) {
            ProjectileSource shooter = ((Projectile) damager).getShooter();
            if (!(shooter instanceof Player)) {
                return;
            }
            damager = (Entity) shooter;
        }
        if (!(damager instanceof Player)) {
            return;
        }
        Player player = (Player) damager;
        LivingEntity livingEntity = (LivingEntity) entity;
        int health = (int) (livingEntity.getHealth() - event.getFinalDamage());
        if (health > 0) {
            MessageUtils.sendActionBar(player,
                    String.format(
                            "§a本次攻击造成 %d 点伤害. §c剩余血量: %d / %d",
                            (int) event.getFinalDamage(),
                            health,
                            (int) livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()
                    )
            );
        } else {
            MessageUtils.sendActionBar(player,
                    String.format(
                            "§a本次攻击造成 %d 点伤害. §c目标已死亡.", (int) event.getFinalDamage()
                    )
            );
        }
    }
}
