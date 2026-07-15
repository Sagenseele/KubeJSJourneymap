package com.miloth.kubejsjourneymap;

import dev.latvian.mods.kubejs.core.PlayerKJS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

/**
 * Client-side helper for querying KubeJS stages of the local player.
 * KubeJS syncs stages to the client, so this is authoritative on the client.
 */
public final class StageHelper {

    private StageHelper() {
    }

    /**
     * @return true if the local player currently has the given stage. An empty stage name means
     * "no gating" and always returns true. A missing player is treated as "not unlocked".
     */
    public static boolean hasStage(String stage) {
        if (stage == null || stage.isBlank()) {
            return true;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return false;
        }

        // KubeJS injects PlayerKJS onto Player at runtime (interface injection), so this cast succeeds.
        return ((PlayerKJS) (Object) player).kjs$getStages().has(stage);
    }
}
