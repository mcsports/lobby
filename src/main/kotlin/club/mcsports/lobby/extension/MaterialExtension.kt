package club.mcsports.lobby.extension

import net.kyori.adventure.key.Key
import org.bukkit.Material

fun Key.toMaterial(): Material {
    return Material.valueOf(this.value().split("/").last().uppercase())
}