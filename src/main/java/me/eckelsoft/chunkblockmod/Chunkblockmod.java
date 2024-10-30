package me.eckelsoft.chunkblockmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Chunkblockmod implements ModInitializer {

    private final Map<UUID, ChunkPos> playerLastChunkMap = new ConcurrentHashMap<>();
    private final List<Block> allBlocks = new ArrayList<>();
    private final Random random = new Random();
    private int tickCounter = 0;

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
        tickCounter++;

        for (ServerWorld world : server.getWorlds()) {
            for (var player : world.getPlayers()) {
                UUID playerId = player.getUuid();
                ChunkPos currentChunk = player.getChunkPos();

                if (playerLastChunkMap.containsKey(playerId)) {
                    ChunkPos lastChunk = playerLastChunkMap.get(playerId);
                    if (!currentChunk.equals(lastChunk)) {
                        playerLastChunkMap.put(playerId, currentChunk);
                        replaceBlocksInChunk(world, currentChunk, player);
                    }
                } else {
                    playerLastChunkMap.put(playerId, currentChunk);
                    replaceBlocksInChunk(world, currentChunk, player);
                }
            }

            // Entferne überschüssige Items alle 60 Sekunden
            if (tickCounter >= 600) { // 20 Ticks pro Sekunde * 60 Sekunden
                for (ServerPlayerEntity player : world.getPlayers()) {
                    removeExcessItems(world, player);
                }
                tickCounter = 0; // Reset für den nächsten Zyklus
            }
        }
    }

    private void replaceBlocksInChunk(ServerWorld world, ChunkPos chunkPos, ServerPlayerEntity player) {
        Block replacementBlock = getReplacementBlockForChunk(chunkPos);

        if (replacementBlock == null) {
            //System.err.println("No replacement block found for chunk: " + chunkPos.x + ", " + chunkPos.z);
            return;
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = -50; y < world.getTopY(); y++) {
                    int worldX = chunkPos.getStartX() + x;
                    int worldZ = chunkPos.getStartZ() + z;
                    BlockPos blockPos = new BlockPos(worldX, y, worldZ);
                    Block currentBlock = world.getBlockState(blockPos).getBlock();

                    if (!BlockExclusions.isExcluded(currentBlock)) {
                        world.setBlockState(blockPos, replacementBlock.getDefaultState(), Block.NOTIFY_ALL);
                    }
                }
            }
        }

        player.sendMessage(
                Text.literal("Chunk (" + chunkPos.x + ", " + chunkPos.z + ") ersetzt durch: " + replacementBlock.getTranslationKey()),
                false
        );
    }

    private Block getReplacementBlockForChunk(ChunkPos chunkPos) {
        long seed = Objects.hash(chunkPos.x, chunkPos.z);
        Random chunkRandom = new Random(seed);
        Block selectedBlock = null;

        while (selectedBlock == null) {
            Block block = allBlocks.get(chunkRandom.nextInt(allBlocks.size()));
            if (!ReplaceToExclusions.isReplaceExcluded(block)) {
                selectedBlock = block;
            }
        }

        return selectedBlock;
    }

    private void removeExcessItems(ServerWorld world, ServerPlayerEntity player) {
        ChunkPos playerChunkPos = player.getChunkPos();

        // Durchlaufe die umliegenden Chunks (7x7 Bereich)
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + x, playerChunkPos.z + z);
                Box chunkBox = new Box(
                        chunkPos.getStartX(), world.getBottomY(), chunkPos.getStartZ(),
                        chunkPos.getEndX() + 1, world.getTopY(), chunkPos.getEndZ() + 1
                );

                List<ItemEntity> itemsToRemove = world.getEntitiesByClass(
                        ItemEntity.class, chunkBox, item -> RemovableItems.isRemovable(item.getStack().getItem())
                );

                itemsToRemove.forEach(ItemEntity::discard);
            }
        }
    }
}
