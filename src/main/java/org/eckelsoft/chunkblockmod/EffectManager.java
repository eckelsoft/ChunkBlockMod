package org.eckelsoft.chunkblockmod;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.ChunkPos;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EffectManager {
    private static final List<RegistryEntry<StatusEffect>> VALID_EFFECTS = new ArrayList<>();

    static {
        for (RegistryEntry<StatusEffect> effect : Registries.STATUS_EFFECT.getIndexedEntries()) {
            if (//!effect.value().isInstant()
                    !effect.equals(StatusEffects.BLINDNESS) &&
                    !effect.equals(StatusEffects.DARKNESS) &&
                    !effect.equals(StatusEffects.NAUSEA)) {
                VALID_EFFECTS.add(effect);
            }
        }
    }

    public static void applyChunkEffect(ServerPlayerEntity player, ChunkPos chunkPos) {
        if (VALID_EFFECTS.isEmpty()) return;

        long seed = (long) chunkPos.x * 49632L + (long) chunkPos.z * 32517L;
        Random chunkRandom = new Random(seed);

        RegistryEntry<StatusEffect> effect = VALID_EFFECTS.get(chunkRandom.nextInt(VALID_EFFECTS.size()));

        int amplifier;
        if (effect.value().isInstant()) {
            amplifier = chunkRandom.nextInt(3);
        } else {
            amplifier = chunkRandom.nextInt(5);
        }

        int durationTicks = 20 * 30;
        player.addStatusEffect(new StatusEffectInstance(effect, durationTicks, amplifier));
    }
}