package me.eckelsoft.chunkblockmod;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.Set;

public class BlockExclusions {
    private static final Set<Block> EXCLUDED_BLOCKS = Set.of(
            // WORLD
            Blocks.AIR,
            Blocks.CAVE_AIR,
            Blocks.VOID_AIR,
            Blocks.STRUCTURE_VOID,
            Blocks.WATER,
            Blocks.LAVA,
            Blocks.FIRE,
            Blocks.END_PORTAL_FRAME,
            Blocks.END_PORTAL,
            Blocks.NETHER_PORTAL,
            Blocks.END_GATEWAY,
            Blocks.SPAWNER,
            // CHESTS
            Blocks.CHEST,
            Blocks.ENDER_CHEST,
            Blocks.TRAPPED_CHEST,
            // BEDS
            Blocks.BLACK_BED,
            Blocks.BLUE_BED,
            Blocks.CYAN_BED,
            Blocks.BROWN_BED,
            Blocks.GRAY_BED,
            Blocks.GREEN_BED,
            Blocks.LIGHT_BLUE_BED,
            Blocks.LIGHT_GRAY_BED,
            Blocks.LIME_BED,
            Blocks.MAGENTA_BED,
            Blocks.ORANGE_BED,
            Blocks.PINK_BED,
            Blocks.PURPLE_BED,
            Blocks.RED_BED,
            Blocks.WHITE_BED,
            Blocks.YELLOW_BED
    );

    public static boolean isExcluded(Block block) {
        return EXCLUDED_BLOCKS.contains(block);
    }
}
