package me.eckelsoft.chunkblockmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Chunkblockmod implements ModInitializer {

    private final Map<UUID, ChunkPos> playerLastChunkMap = new ConcurrentHashMap<>();
    private final List<Block> allBlocks = new ArrayList<>();
    private final Set<Block> replaceToExclusions = Set.of(
            Blocks.AIR,
            Blocks.SAND,
            Blocks.GRAVEL,
            Blocks.WATER,
            Blocks.TNT,
            Blocks.SUGAR_CANE,
            Blocks.TORCH,
            Blocks.POWDER_SNOW,
            Blocks.CAMPFIRE,
            Blocks.SOUL_CAMPFIRE,
            Blocks.SOUL_TORCH,
            Blocks.SOUL_FIRE,
            Blocks.SEA_PICKLE,
            Blocks.RED_SAND,
            Blocks.PINK_CONCRETE_POWDER,
            Blocks.BLACK_CONCRETE_POWDER,
            Blocks.BLUE_CONCRETE_POWDER,
            Blocks.BROWN_CONCRETE_POWDER,
            Blocks.GRAY_CONCRETE_POWDER,
            Blocks.CYAN_CONCRETE_POWDER,
            Blocks.GREEN_CONCRETE_POWDER,
            Blocks.LIGHT_BLUE_CONCRETE_POWDER,
            Blocks.LIGHT_GRAY_CONCRETE_POWDER,
            Blocks.LIME_CONCRETE_POWDER,
            Blocks.MAGENTA_CONCRETE_POWDER,
            Blocks.ORANGE_CONCRETE_POWDER,
            Blocks.PURPLE_CONCRETE_POWDER,
            Blocks.RED_CONCRETE_POWDER,
            Blocks.WHITE_CONCRETE_POWDER,
            Blocks.ALLIUM,
            Blocks.AZURE_BLUET,
            Blocks.BLUE_ORCHID,
            Blocks.CORNFLOWER,
            Blocks.DANDELION,
            Blocks.LILAC,
            Blocks.LILY_OF_THE_VALLEY,
            Blocks.ORANGE_TULIP,
            Blocks.OXEYE_DAISY,
            Blocks.PEONY,
            Blocks.PINK_TULIP,
            Blocks.POPPY,
            Blocks.RED_TULIP,
            Blocks.ROSE_BUSH,
            Blocks.SUNFLOWER,
            Blocks.WHITE_TULIP,
            Blocks.WITHER_ROSE,
            Blocks.BROWN_MUSHROOM,
            Blocks.RED_MUSHROOM,
            Blocks.BEETROOTS,
            Blocks.CRIMSON_ROOTS,
            Blocks.HANGING_ROOTS,
            Blocks.MANGROVE_ROOTS,
            Blocks.WARPED_ROOTS,
            Blocks.MUDDY_MANGROVE_ROOTS,
            Blocks.DEAD_BUSH,
            Blocks.FERN,
            Blocks.GRASS,
            Blocks.LARGE_FERN,
            Blocks.LILY_PAD,
            Blocks.TALL_GRASS,
            Blocks.VINE,
            Blocks.CAVE_VINES,
            Blocks.SPRUCE_SAPLING,
            Blocks.ACACIA_SAPLING,
            Blocks.BAMBOO_SAPLING,
            Blocks.BIRCH_SAPLING,
            Blocks.CHERRY_SAPLING,
            Blocks.DARK_OAK_SAPLING,
            Blocks.JUNGLE_SAPLING,
            Blocks.OAK_SAPLING,
            Blocks.AZALEA,
            Blocks.FLOWERING_AZALEA,
            Blocks.MANGROVE_PROPAGULE,
            Blocks.FIRE,
            Blocks.LAVA,
            Blocks.BARRIER,
            Blocks.WEEPING_VINES,
            Blocks.CARROTS,
            Blocks.POTATOES,
            Blocks.PISTON,
            Blocks.PISTON_HEAD,
            Blocks.FROGSPAWN,
            Blocks.CANDLE,
            Blocks.TRIPWIRE,
            Blocks.TRIPWIRE_HOOK,
            Blocks.RAIL,
            Blocks.ACTIVATOR_RAIL,
            Blocks.DETECTOR_RAIL,
            Blocks.POWERED_RAIL,
            Blocks.REPEATER,
            Blocks.PUMPKIN_STEM,
            Blocks.CARVED_PUMPKIN,
            Blocks.ATTACHED_PUMPKIN_STEM,
            Blocks.COMPARATOR,
            Blocks.DRAGON_EGG,
            Blocks.SNIFFER_EGG,
            Blocks.PINK_CANDLE,
            Blocks.MELON,
            Blocks.PUMPKIN,
            Blocks.BUDDING_AMETHYST,
            Blocks.AMETHYST_CLUSTER,
            Blocks.LARGE_AMETHYST_BUD,
            Blocks.MEDIUM_AMETHYST_BUD,
            Blocks.SMALL_AMETHYST_BUD,
            Blocks.REPEATING_COMMAND_BLOCK,
            Blocks.LIGHT_BLUE_CARPET,
            Blocks.LIGHT_GRAY_CARPET,
            Blocks.CYAN_CARPET,
            Blocks.BLUE_CARPET,
            Blocks.BROWN_CARPET,
            Blocks.GRAY_CARPET,
            Blocks.LIME_CARPET,
            Blocks.MAGENTA_CARPET,
            Blocks.BLACK_CARPET,
            Blocks.GREEN_CARPET,
            Blocks.ORANGE_CARPET,
            Blocks.PINK_CARPET,
            Blocks.RED_CARPET,
            Blocks.PURPLE_CARPET,
            Blocks.WHITE_CARPET,
            Blocks.YELLOW_CARPET,
            Blocks.SMALL_DRIPLEAF,
            Blocks.BIG_DRIPLEAF,
            Blocks.SPRUCE_BUTTON,
            Blocks.WARPED_FUNGUS,
            Blocks.CRIMSON_FUNGUS,
            Blocks.POLISHED_BLACKSTONE_BUTTON,
            Blocks.DARK_OAK_BUTTON,
            Blocks.WARPED_BUTTON,
            Blocks.STONE_BUTTON,
            Blocks.OAK_BUTTON,
            Blocks.CHERRY_BUTTON,
            Blocks.BAMBOO_BUTTON,
            Blocks.BIRCH_BUTTON,
            Blocks.ACACIA_BUTTON,
            Blocks.CRIMSON_BUTTON,
            Blocks.JUNGLE_BUTTON,
            Blocks.MANGROVE_BUTTON,
            Blocks.ACACIA_FENCE_GATE,
            Blocks.DARK_OAK_FENCE,
            Blocks.BAMBOO_FENCE_GATE,
            Blocks.BIRCH_FENCE_GATE,
            Blocks.CHERRY_FENCE_GATE,
            Blocks.CRIMSON_FENCE_GATE,
            Blocks.DARK_OAK_FENCE_GATE,
            Blocks.JUNGLE_FENCE_GATE,
            Blocks.PITCHER_CROP,
            Blocks.MANGROVE_FENCE_GATE,
            Blocks.OAK_FENCE_GATE,
            Blocks.SPRUCE_FENCE_GATE,
            Blocks.WARPED_FENCE_GATE,
            Blocks.ACACIA_FENCE,
            Blocks.BAMBOO_FENCE,
            Blocks.BIRCH_FENCE,
            Blocks.CHERRY_FENCE,
            Blocks.CRIMSON_FENCE,
            Blocks.OAK_FENCE,
            Blocks.SPRUCE_FENCE,
            Blocks.MANGROVE_FENCE,
            Blocks.JUNGLE_FENCE,
            Blocks.WARPED_FENCE,
            Blocks.CREEPER_HEAD,
            Blocks.DRAGON_HEAD,
            Blocks.PIGLIN_HEAD,
            Blocks.PLAYER_HEAD,
            Blocks.ZOMBIE_HEAD,
            Blocks.CREEPER_WALL_HEAD,
            Blocks.DRAGON_WALL_HEAD,
            Blocks.PIGLIN_WALL_HEAD,
            Blocks.ZOMBIE_WALL_HEAD,
            Blocks.PLAYER_WALL_HEAD,
            Blocks.ACACIA_SIGN,
            Blocks.ACACIA_WALL_HANGING_SIGN,
            Blocks.ACACIA_WALL_SIGN,
            Blocks.BIRCH_WALL_SIGN,
            Blocks.BIRCH_SIGN,
            Blocks.BAMBOO_HANGING_SIGN,
            Blocks.CHERRY_SIGN,
            Blocks.JUNGLE_SIGN,
            Blocks.CRIMSON_SIGN,
            Blocks.MANGROVE_SIGN,
            Blocks.BAMBOO_SIGN,
            Blocks.OAK_SIGN,
            Blocks.SPRUCE_SIGN,
            Blocks.WARPED_SIGN,
            Blocks.CHERRY_HANGING_SIGN,
            Blocks.JUNGLE_HANGING_SIGN,
            Blocks.CRIMSON_HANGING_SIGN,
            Blocks.MANGROVE_HANGING_SIGN,
            Blocks.OAK_HANGING_SIGN,
            Blocks.SPRUCE_HANGING_SIGN,
            Blocks.WARPED_HANGING_SIGN,
            Blocks.KELP,
            Blocks.KELP_PLANT,
            Blocks.BAMBOO_WALL_SIGN,
            Blocks.CHERRY_WALL_SIGN,
            Blocks.CRIMSON_WALL_SIGN,
            Blocks.DARK_OAK_WALL_HANGING_SIGN,
            Blocks.SPRUCE_WALL_HANGING_SIGN,
            Blocks.DARK_OAK_WALL_SIGN,
            Blocks.JUNGLE_WALL_SIGN,
            Blocks.MANGROVE_WALL_SIGN,
            Blocks.OAK_WALL_SIGN,
            Blocks.SPRUCE_WALL_SIGN,
            Blocks.WARPED_WALL_SIGN,
            Blocks.CRIMSON_WALL_HANGING_SIGN,
            Blocks.WARPED_WALL_HANGING_SIGN,
            Blocks.OAK_WALL_HANGING_SIGN,
            Blocks.JUNGLE_WALL_HANGING_SIGN,
            Blocks.CHERRY_WALL_HANGING_SIGN,
            Blocks.MANGROVE_WALL_HANGING_SIGN,
            Blocks.BIRCH_WALL_HANGING_SIGN,
            Blocks.BAMBOO_WALL_HANGING_SIGN,
            Blocks.CAVE_VINES_PLANT,
            Blocks.TWISTING_VINES_PLANT,
            Blocks.CHORUS_PLANT,
            Blocks.PITCHER_PLANT,
            Blocks.WEEPING_VINES_PLANT,
            Blocks.REDSTONE_TORCH,
            Blocks.REDSTONE_WIRE,
            Blocks.REDSTONE_WALL_TORCH,
            Blocks.LEVER,
            Blocks.STICKY_PISTON,
            Blocks.SUSPICIOUS_GRAVEL,
            Blocks.BAMBOO,
            Blocks.END_ROD,
            Blocks.WALL_TORCH,
            Blocks.SOUL_WALL_TORCH,
            Blocks.ACACIA_DOOR,
            Blocks.BAMBOO_DOOR,
            Blocks.DARK_OAK_DOOR,
            Blocks.BIRCH_DOOR,
            Blocks.CHERRY_DOOR,
            Blocks.CRIMSON_DOOR,
            Blocks.IRON_DOOR,
            Blocks.JUNGLE_DOOR,
            Blocks.MANGROVE_DOOR,
            Blocks.OAK_DOOR,
            Blocks.SPRUCE_DOOR,
            Blocks.WARPED_DOOR,
            Blocks.TORCHFLOWER,
            Blocks.TORCHFLOWER_CROP,
            Blocks.CANDLE_CAKE,
            Blocks.CYAN_CANDLE,
            Blocks.BLUE_CANDLE,
            Blocks.BLACK_CANDLE,
            Blocks.BROWN_CANDLE,
            Blocks.GRAY_CANDLE,
            Blocks.GREEN_CANDLE,
            Blocks.LIGHT_BLUE_CANDLE,
            Blocks.LIGHT_GRAY_CANDLE,
            Blocks.LIME_CANDLE,
            Blocks.MAGENTA_CANDLE,
            Blocks.ORANGE_CANDLE,
            Blocks.BLACK_CANDLE_CAKE,
            Blocks.BLUE_CANDLE_CAKE,
            Blocks.CYAN_CANDLE_CAKE,
            Blocks.LIGHT_GRAY_CANDLE_CAKE,
            Blocks.LIME_CANDLE_CAKE,
            Blocks.GREEN_CANDLE_CAKE,
            Blocks.RED_CANDLE_CAKE,
            Blocks.GRAY_CANDLE_CAKE,
            Blocks.MAGENTA_CANDLE_CAKE,
            Blocks.BROWN_CANDLE_CAKE,
            Blocks.PURPLE_CANDLE_CAKE,
            Blocks.WHITE_CANDLE_CAKE,
            Blocks.YELLOW_CANDLE_CAKE,
            Blocks.PURPLE_CANDLE,
            Blocks.WHITE_CANDLE,
            Blocks.RED_CANDLE,
            Blocks.YELLOW_CANDLE,
            Blocks.LIGHT_BLUE_CANDLE_CAKE,
            Blocks.ORANGE_CANDLE_CAKE,
            Blocks.PINK_CANDLE_CAKE,
            Blocks.BLACK_BANNER,
            Blocks.ORANGE_BANNER,
            Blocks.YELLOW_WALL_BANNER,
            Blocks.YELLOW_BANNER,
            Blocks.WHITE_WALL_BANNER,
            Blocks.WHITE_BANNER,
            Blocks.RED_WALL_BANNER,
            Blocks.RED_BANNER,
            Blocks.PURPLE_WALL_BANNER,
            Blocks.PURPLE_BANNER,
            Blocks.PINK_WALL_BANNER,
            Blocks.PINK_BANNER,
            Blocks.ORANGE_WALL_BANNER,
            Blocks.MAGENTA_BANNER,
            Blocks.LIME_WALL_BANNER,
            Blocks.LIME_BANNER,
            Blocks.BLUE_BANNER,
            Blocks.BROWN_BANNER,
            Blocks.BLUE_WALL_BANNER,
            Blocks.BLACK_WALL_BANNER,
            Blocks.BROWN_WALL_BANNER,
            Blocks.CYAN_BANNER,
            Blocks.CYAN_WALL_BANNER,
            Blocks.GRAY_BANNER,
            Blocks.GRAY_WALL_BANNER,
            Blocks.GREEN_BANNER,
            Blocks.GREEN_WALL_BANNER,
            Blocks.LIGHT_BLUE_BANNER,
            Blocks.LIGHT_BLUE_WALL_BANNER,
            Blocks.LIGHT_GRAY_BANNER,
            Blocks.LIGHT_GRAY_WALL_BANNER,
            Blocks.MAGENTA_WALL_BANNER,
            Blocks.ANVIL,
            Blocks.CHIPPED_ANVIL,
            Blocks.DAMAGED_ANVIL,
            Blocks.CAKE,
            Blocks.SCAFFOLDING,
            Blocks.NETHER_WART,
            Blocks.ACACIA_TRAPDOOR,
            Blocks.BAMBOO_TRAPDOOR,
            Blocks.BIRCH_TRAPDOOR,
            Blocks.CHERRY_TRAPDOOR,
            Blocks.CRIMSON_TRAPDOOR,
            Blocks.IRON_TRAPDOOR,
            Blocks.DARK_OAK_TRAPDOOR,
            Blocks.OAK_TRAPDOOR,
            Blocks.JUNGLE_TRAPDOOR,
            Blocks.MANGROVE_TRAPDOOR,
            Blocks.SPRUCE_TRAPDOOR,
            Blocks.WARPED_TRAPDOOR,
            Blocks.ACACIA_PRESSURE_PLATE,
            Blocks.BAMBOO_PRESSURE_PLATE,
            Blocks.BIRCH_PRESSURE_PLATE,
            Blocks.CHERRY_PRESSURE_PLATE,
            Blocks.CRIMSON_PRESSURE_PLATE,
            Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Blocks.JUNGLE_PRESSURE_PLATE,
            Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Blocks.MANGROVE_PRESSURE_PLATE,
            Blocks.OAK_PRESSURE_PLATE,
            Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE,
            Blocks.SPRUCE_PRESSURE_PLATE,
            Blocks.WARPED_PRESSURE_PLATE,
            Blocks.STONE_PRESSURE_PLATE,
            Blocks.DARK_OAK_PRESSURE_PLATE,
            Blocks.POTTED_ALLIUM,
            Blocks.POTTED_BAMBOO,
            Blocks.POTTED_ACACIA_SAPLING,
            Blocks.POTTED_AZALEA_BUSH,
            Blocks.POTTED_CACTUS,
            Blocks.POTTED_AZURE_BLUET,
            Blocks.POTTED_BIRCH_SAPLING,
            Blocks.POTTED_BLUE_ORCHID,
            Blocks.POTTED_BROWN_MUSHROOM,
            Blocks.POTTED_CHERRY_SAPLING,
            Blocks.POTTED_CORNFLOWER,
            Blocks.POTTED_WITHER_ROSE,
            Blocks.POTTED_CRIMSON_FUNGUS,
            Blocks.POTTED_DANDELION,
            Blocks.POTTED_CRIMSON_ROOTS,
            Blocks.POTTED_DARK_OAK_SAPLING,
            Blocks.POTTED_JUNGLE_SAPLING,
            Blocks.POTTED_WHITE_TULIP,
            Blocks.POTTED_WARPED_ROOTS,
            Blocks.POTTED_WARPED_FUNGUS,
            Blocks.POTTED_TORCHFLOWER,
            Blocks.POTTED_SPRUCE_SAPLING,
            Blocks.POTTED_RED_TULIP,
            Blocks.POTTED_RED_MUSHROOM,
            Blocks.POTTED_POPPY,
            Blocks.POTTED_PINK_TULIP,
            Blocks.POTTED_OXEYE_DAISY,
            Blocks.POTTED_ORANGE_TULIP,
            Blocks.POTTED_OAK_SAPLING,
            Blocks.POTTED_MANGROVE_PROPAGULE,
            Blocks.POTTED_LILY_OF_THE_VALLEY,
            Blocks.POTTED_FLOWERING_AZALEA_BUSH,
            Blocks.POTTED_FERN,
            Blocks.POTTED_DEAD_BUSH,

            Blocks.WHEAT
    );
    //private final Random random = new Random();

    @Override
    public void onInitialize() {
        System.out.println("MAIN!!!! Chunk Block Mod loaded up!!!");
        collectAllBlocks();
        ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);
    }

    private void collectAllBlocks() {
        Registries.BLOCK.forEach(allBlocks::add);
        System.out.println("Total blocks collected: " + allBlocks.size());
    }

    private void onServerTick(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            for (var player : world.getPlayers()) {
                UUID playerId = player.getUuid();
                ChunkPos currentChunk = player.getChunkPos();

                if (playerLastChunkMap.containsKey(playerId)) {
                    ChunkPos lastChunk = playerLastChunkMap.get(playerId);
                    if (!currentChunk.equals(lastChunk)) {
                        playerLastChunkMap.put(playerId, currentChunk);
                        replaceBlocksInChunk(world, currentChunk);
                    }
                } else {
                    playerLastChunkMap.put(playerId, currentChunk);
                    replaceBlocksInChunk(world, currentChunk);
                }
            }
        }
    }

    private void replaceBlocksInChunk(ServerWorld world, ChunkPos chunkPos) {
        Block replacementBlock = getReplacementBlockForChunk(chunkPos);

        if (replacementBlock == null) {
            System.err.println("No replacement block found for chunk: " + chunkPos.x + ", " + chunkPos.z);
            return;
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < world.getTopY(); y++) {
                    int worldX = chunkPos.getStartX() + x;
                    int worldZ = chunkPos.getStartZ() + z;
                    BlockPos blockPos = new BlockPos(worldX, y, worldZ);
                    Block currentBlock = world.getBlockState(blockPos).getBlock();

                    if (!BlockExclusions.isExcluded(currentBlock)) {
                        world.setBlockState(blockPos, replacementBlock.getDefaultState());
                    }
                }
            }
        }
    }

    private Block getReplacementBlockForChunk(ChunkPos chunkPos) {
        long seed = Objects.hash(chunkPos.x, chunkPos.z);
        Random chunkRandom = new Random(seed);
        Block selectedBlock = null;

        while (selectedBlock == null) {
            Block block = allBlocks.get(chunkRandom.nextInt(allBlocks.size()));
            if (!replaceToExclusions.contains(block)) {
                selectedBlock = block;
            }
        }

        return selectedBlock;
    }
}
