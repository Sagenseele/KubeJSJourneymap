package com.miloth.journeymapstages;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// Client config: which KubeJS stage unlocks JourneyMap.
@EventBusSubscriber(modid = Journeymapstages.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<String> JOURNEYMAP_STAGE = BUILDER
            .comment("The KubeJS stage a player must have before JourneyMap (fullscreen map, menus and minimap) becomes usable.",
                    "Leave empty to disable gating entirely.")
            .define("journeyMapStage", "journeymap");

    static final ModConfigSpec SPEC = BUILDER.build();

    public static String journeyMapStage = "journeymap";

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        journeyMapStage = JOURNEYMAP_STAGE.get();
    }
}
