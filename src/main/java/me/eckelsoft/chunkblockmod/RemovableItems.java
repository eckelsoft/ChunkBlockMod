package me.eckelsoft.chunkblockmod;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Set;

public class RemovableItems {
    private static final Set<Item> REMOVABLE_ITEMS = Set.of(
            Items.WHEAT_SEEDS,
            Items.PINK_PETALS,
            Items.ALLIUM,
            Items.AZURE_BLUET,
            Items.BLUE_ORCHID,
            Items.CORNFLOWER,
            Items.DANDELION,
            Items.LILAC,
            Items.COCOA_BEANS,
            Items.BEETROOT_SEEDS,
            Items.MELON_SEEDS,
            Items.PUMPKIN_SEEDS,
            Items.TORCHFLOWER_SEEDS,
            Items.LILY_OF_THE_VALLEY,
            Items.ORANGE_TULIP,
            Items.OXEYE_DAISY,
            Items.PEONY,
            Items.PINK_TULIP,
            Items.POPPY,
            Items.RED_TULIP,
            Items.ROSE_BUSH,
            Items.SUNFLOWER,
            Items.WHITE_TULIP
    );

    public static boolean isRemovable(Item item) {
        return REMOVABLE_ITEMS.contains(item);
    }
}
