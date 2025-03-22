package club.mcsports.lobby.listener

import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.util.Party
import com.noxcrew.interfaces.InterfacesConstants
import kotlinx.coroutines.launch
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

class PlayerInteractListener(private val gameSelector: GuiGameSelector, private val party: Party) : Listener {

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

                "open_next_party_page" -> {
                    party.openNextPage(event.player.uniqueId)
                }

                "open_previous_party_page" -> {
                    party.openPreviousPage(event.player.uniqueId)
                }

                else -> return
            }
        }
    }
}