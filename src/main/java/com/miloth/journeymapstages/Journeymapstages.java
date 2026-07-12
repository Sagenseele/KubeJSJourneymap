package com.miloth.journeymapstages;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Journeymapstages.MODID)
public class Journeymapstages {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "journeymapstages";

    public Journeymapstages(ModContainer modContainer) {
        // Register the client config that holds the gating stage name.
        // All gameplay logic lives in @EventBusSubscriber classes (JourneyMapGate), so no manual
        // event registration is needed here.
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }
}
