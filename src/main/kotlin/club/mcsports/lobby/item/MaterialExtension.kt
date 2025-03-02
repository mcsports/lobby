package club.mcsports.lobby.item

import net.kyori.adventure.key.Key
import org.bukkit.Material

fun Key.toMaterial(): Material {
    return Material.valueOf(this.value().split("/").last().uppercase())
}