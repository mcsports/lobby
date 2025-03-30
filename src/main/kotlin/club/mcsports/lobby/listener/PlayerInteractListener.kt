package club.mcsports.lobby.listener

import club.mcsports.lobby.gui.GuiGameSelector
import com.noxcrew.interfaces.InterfacesConstants
import kotlinx.coroutines.launch
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

class PlayerInteractListener(private val gameSelector: GuiGameSelector) : Listener {

    @EventHandler
    fun handlePlayerInteract(event: PlayerInteractEvent) {
        val meta = event.item?.itemMeta ?: return
        meta.persistentDataContainer.get(NamespacedKey("mcsports", "lobby/action"), PersistentDataType.STRING)?.let {

            when (it) {
                "open_game_selector" -> {
                    InterfacesConstants.SCOPE.launch {
                        gameSelector.gui.open(event.player)
                    }
                }

                else -> return
            }
        }
    }
}