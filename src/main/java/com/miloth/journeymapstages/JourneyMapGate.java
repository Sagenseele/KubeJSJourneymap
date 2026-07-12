package com.miloth.journeymapstages;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

/**
 * Client-side gating of JourneyMap behind a KubeJS stage:
 * <ul>
 *     <li>Blocks opening any JourneyMap screen and shows an action-bar message.</li>
 *     <li>Hides the minimap until the stage is unlocked.</li>
 * </ul>
 */
@EventBusSubscriber(modid = Journeymapstages.MODID, value = Dist.CLIENT)
public final class JourneyMapGate {

    private static final Component LOCKED_MESSAGE = Component.translatable("journeymapstages.locked");

    /** null = not evaluated since joining a world; used so we never fight the user's minimap toggle once unlocked. */
    private static Boolean wasLocked = null;

    private JourneyMapGate() {
    }

    @SubscribeEvent
    public static void onScreenOpening(ScreenEvent.Opening event) {
        if (JourneyMapUi.isJourneyMapScreen(event.getNewScreen())
                && !StageHelper.hasStage(Config.journeyMapStage)) {
            event.setCanceled(true);
            Minecraft.getInstance().gui.setOverlayMessage(LOCKED_MESSAGE, false);
        }
    }

    /**
     * Enforce the minimap gate once per frame, before JourneyMap draws its HUD overlay. Doing this
     * per-frame (rather than on a tick) means that toggling the minimap on (e.g. Ctrl+J) while gated
     * is reverted in the same frame, so the minimap never flickers into view.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderGuiPre(RenderGuiEvent.Pre event) {
        if (!JourneyMapUi.isPresent() || Minecraft.getInstance().player == null) {
            return;
        }

        boolean locked = !StageHelper.hasStage(Config.journeyMapStage);
        if (locked) {
            // Keep the minimap forced off while gated (only writes when it is actually on).
            if (JourneyMapUi.isMinimapEnabled()) {
                JourneyMapUi.setMinimapEnabled(false);
            }
        } else if (Boolean.TRUE.equals(wasLocked)) {
            // Just transitioned locked -> unlocked: re-enable once, then leave it to the user.
            JourneyMapUi.setMinimapEnabled(true);
        }
        wasLocked = locked;
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        // Re-evaluate fresh on the next world join without forcing the minimap on.
        wasLocked = null;
    }
}
