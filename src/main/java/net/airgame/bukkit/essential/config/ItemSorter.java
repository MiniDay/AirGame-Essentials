package net.airgame.bukkit.essential.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;

/**
 * 物品排序器
 */
public class ItemSorter implements Comparator<ItemStack> {
    public static final ItemSorter instance = new ItemSorter();

    private ItemSorter() {
    }

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        if (o1 == o2) return 0;
        if (o1 == null || o1.getType() == Material.AIR) return 1;
        if (o2 == null || o2.getType() == Material.AIR) return -1;
        if (o1.getType() == o1.getType()) {
            return 0;
        }
        Material type1 = o1.getType();
        Material type2 = o2.getType();
        int i = Boolean.compare(type1.isSolid(), type2.isSolid());
        if (i != 0) {
            return i;
        }
        i = Boolean.compare(type1.isItem(), type2.isItem());
        if (i != 0) {
            return i;
        }
        return o1.getType().compareTo(o2.getType());
    }
}
