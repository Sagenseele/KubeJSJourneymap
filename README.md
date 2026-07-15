# KubeJS Journeymap

A client-side NeoForge **1.21.1** mod that locks JourneyMap behind a [KubeJS](https://kubejs.com/) stage. While the stage is missing, JourneyMap's GUIs can't be
opened and the minimap stays hidden.

**Requires:** KubeJS and JourneyMap obviously.

## Usage

1. Set the gating stage in `config/kubejsjourneymap-client.toml`:
   ```toml
   kubejsjourneymap = "journeymap"   # default; leave empty to disable gating
   ```
2. Grant that stage to a player via KubeJS when they should unlock JourneyMap, e.g. in a server script:
   ```js
   player.stages.add("journeymap")
   ```
