package org.eckelsoft.chunkblockmod;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Block> PRESERVED_BLOCKS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("chunkblockmod", "preserved_blocks"));
    public static final TagKey<Block> INVALID_REPLACEMENT_BLOCKS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("chunkblockmod", "invalid_replacements"));
    public static final TagKey<Enchantment> CHUNK_WALKER_EXCLUSIVE_SET = TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("chunkblockmod", "exclusive/chunk_walker"));
}