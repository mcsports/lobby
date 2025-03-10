package club.mcsports.lobby.config

import club.mcsports.lobby.location.SpawnPoint
import org.bukkit.Location
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class Config(
    val spawnPoints: MutableMap<SpawnPoint, Location> = mutableMapOf()
)