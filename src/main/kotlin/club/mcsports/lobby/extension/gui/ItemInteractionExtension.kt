package club.mcsports.lobby.extension.gui

import club.mcsports.lobby.util.ItemInteraction
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

fun PersistentDataContainer.set(
    key: NamespacedKey,
    interaction: ItemInteraction
) {
    set(
        key,
        PersistentDataType.STRING,
        interaction.name
    )
}