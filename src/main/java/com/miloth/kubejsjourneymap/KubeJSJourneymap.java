package com.miloth.kubejsjourneymap;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(KubeJSJourneymap.MODID)
public class KubeJSJourneymap {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "kubejsjourneymap";

    public KubeJSJourneymap(ModContainer modContainer) {
        // Register the client config that holds the gating stage name.
        // All gameplay logic lives in @EventBusSubscriber classes (JourneyMapGate), so no manual
        // event registration is needed here.
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }
}
