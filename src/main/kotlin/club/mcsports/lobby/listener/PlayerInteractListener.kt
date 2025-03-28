package club.mcsports.lobby.listener

import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.util.Party
import com.noxcrew.interfaces.InterfacesConstants
import com.noxcrew.interfaces.view.PlayerInterfaceView
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
                "prev_party_arrow" -> {
                    InterfacesConstants.SCOPE.launch {
                        val result = PlayerJoinListener.playerInterfaces[event.player.uniqueId]
                    }
                }
                else -> return
            }
        }
    }
}