# Journey Map Stages

A client-side NeoForge mod that locks JourneyMaps functionalities behind a [KubeJS](https://kubejs.com/) stage. If the stage is missing, JourneyMap's GUIs can't be opened and the minimap stays hidden.

**Requires:** KubeJS and JourneyMap obviously.

## Usage

Set the gating stage in `config/journeymapstages-client.toml`:
```toml
journeyMapStage = "journeymap"   # default; leave empty to disable gating
```
Grant that stage to a player via KubeJS when they should unlock JourneyMap, e.g. in a server script:
```js
player.stages.add("journeymap")
```
Or add it ingame via
```
/kubejs stages add <Player> journeymap
/kubejs stages remove <Player> journeymap
```
