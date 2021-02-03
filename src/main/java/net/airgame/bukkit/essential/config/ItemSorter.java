package net.airgame.bukkit.essential.config;

import net.airgame.bukkit.api.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;

/**
 * 物品排序器
 */
public class ItemSorter implements Comparator<ItemStack> {
    public static final ItemSorter INSTANCE = new ItemSorter();

    private ItemSorter() {
    }

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        if (o1 == o2) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        Material type1 = o1.getType();
        Material type2 = o2.getType();
        if (type1 == type2) {
            return 0;
        }
        int i = -Boolean.compare(ItemUtils.isDamageable(type1), ItemUtils.isDamageable(type2));
        if (i != 0) {
            return i;
        }
        i = -Boolean.compare(type1.isSolid(), type2.isSolid());
        if (i != 0) {
            return i;
        }
        i = -Boolean.compare(type1.isItem(), type2.isItem());
        if (i != 0) {
            return i;
        }
        return o1.getType().name().compareTo(o2.getType().name());
    }
}
