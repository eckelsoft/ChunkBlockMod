package me.eckelsoft.chunkblockmod;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.Set;

public class BlockExclusions {
    private static final Set<Block> EXCLUDED_BLOCKS = Set.of(
            Blocks.AIR,
            Blocks.WATER,
            Blocks.LAVA,
            Blocks.FIRE,
            Blocks.END_PORTAL_FRAME,
            Blocks.SPAWNER,
            Blocks.END_PORTAL
    );

    public static boolean isExcluded(Block block) {
        return EXCLUDED_BLOCKS.contains(block);
    }
}
