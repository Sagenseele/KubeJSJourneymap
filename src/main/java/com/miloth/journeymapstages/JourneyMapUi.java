package com.miloth.journeymapstages;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.Screen;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Thin reflection wrapper around JourneyMap's client UI. JourneyMap is closed source and not
 * published to a public maven, so we access {@code journeymap.client.ui.UIManager} (an enum
 * singleton) reflectively. If JourneyMap is absent, everything degrades to a safe no-op.
 */
public final class JourneyMapUi {

    private static final Logger LOGGER = LogUtils.getLogger();

    /** All JourneyMap screens (fullscreen map, waypoint manager/editor, options, ...) live here. */
    private static final String SCREEN_PACKAGE_PREFIX = "journeymap.client.ui.";

    private static boolean resolved = false;
    private static Object uiManagerInstance;
    private static Method isMiniMapEnabled;
    private static Method setMiniMapEnabled;

    private JourneyMapUi() {
    }

    private static synchronized void resolve() {
        if (resolved) {
            return;
        }
        resolved = true;
        try {
            Class<?> uiManager = Class.forName("journeymap.client.ui.UIManager");
            Field instance = uiManager.getField("INSTANCE");
            uiManagerInstance = instance.get(null);
            isMiniMapEnabled = uiManager.getMethod("isMiniMapEnabled");
            setMiniMapEnabled = uiManager.getMethod("setMiniMapEnabled", boolean.class);
        } catch (ClassNotFoundException e) {
            LOGGER.info("JourneyMap not present - minimap gating disabled.");
            uiManagerInstance = null;
        } catch (ReflectiveOperationException e) {
            LOGGER.warn("Failed to hook JourneyMap UIManager - minimap gating disabled.", e);
            uiManagerInstance = null;
        }
    }

    /** @return true if JourneyMap's minimap controls were found and can be driven. */
    public static boolean isPresent() {
        resolve();
        return uiManagerInstance != null;
    }

    public static boolean isMinimapEnabled() {
        if (!isPresent()) {
            return false;
        }
        try {
            return (boolean) isMiniMapEnabled.invoke(uiManagerInstance);
        } catch (ReflectiveOperationException e) {
            LOGGER.warn("JourneyMap isMiniMapEnabled() call failed.", e);
            return false;
        }
    }

    public static void setMinimapEnabled(boolean enabled) {
        if (!isPresent()) {
            return;
        }
        try {
            setMiniMapEnabled.invoke(uiManagerInstance, enabled);
        } catch (ReflectiveOperationException e) {
            LOGGER.warn("JourneyMap setMiniMapEnabled({}) call failed.", enabled, e);
        }
    }

    /** @return true if the given screen is any JourneyMap UI screen. */
    public static boolean isJourneyMapScreen(Screen screen) {
        return screen != null && screen.getClass().getName().startsWith(SCREEN_PACKAGE_PREFIX);
    }
}
