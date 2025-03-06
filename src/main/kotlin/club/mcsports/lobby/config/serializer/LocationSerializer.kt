package club.mcsports.lobby.config.serializer

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

class LocationSerializer : TypeSerializer<Location> {
    override fun serialize(type: Type, obj: Location?, node: ConfigurationNode) {
        if (obj == null) {
            node.set(null)
            return
        }
        node.node("world").set(obj.world?.name)
        node.node("x").set(obj.x)
        node.node("y").set(obj.y)
        node.node("z").set(obj.z)
        node.node("yaw").set(obj.yaw)
        node.node("pitch").set(obj.pitch)
    }

    override fun deserialize(type: Type, node: ConfigurationNode): Location {
        val worldName = node.node("world").string ?: throw SerializationException("World name is missing!")
        val world: World = Bukkit.getWorld(worldName) ?: throw SerializationException("World '$worldName' not found!")
        val x = node.node("x").double
        val y = node.node("y").double
        val z = node.node("z").double
        val yaw = node.node("yaw").float
        val pitch = node.node("pitch").float
        return Location(world, x, y, z, yaw, pitch)
    }
}
