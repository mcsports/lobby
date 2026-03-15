package club.mcsports.lobby.extension

import club.mcsports.lobby.extension.gui.set
import club.mcsports.lobby.util.ItemInteraction
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

private val actionKey = NamespacedKey("mcsports", "lobby/action")

var ItemStack.interaction: ItemInteraction?
    get() = itemMeta.persistentDataContainer.get(actionKey, PersistentDataType.STRING)?.let { ItemInteraction.valueOf(it) }
    set(value) {
        value?.let {
            editMeta { meta -> meta.persistentDataContainer.set(actionKey, it) }
        }
    }