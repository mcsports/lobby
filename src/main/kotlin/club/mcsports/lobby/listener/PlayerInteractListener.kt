package club.mcsports.lobby.listener

import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.gui.GuiTest
import club.mcsports.lobby.util.ItemInteraction
import com.noxcrew.interfaces.InterfacesConstants
import kotlinx.coroutines.launch
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

class PlayerInteractListener(private val gameSelector: GuiGameSelector) : Listener {

    private val testGui = GuiTest()

    @EventHandler
    fun handlePlayerInteract(event: PlayerInteractEvent) {
        val meta = event.item?.itemMeta ?: return
        meta.persistentDataContainer.get(NamespacedKey("mcsports", "lobby/action"), PersistentDataType.STRING)?.let { itemClickType ->

            when (itemClickType) {
                ItemInteraction.OPEN_GAME_SELECTOR.name -> {
                    InterfacesConstants.SCOPE.launch {
                        gameSelector.gui.open(event.player)
                    }
                }

                else -> return
            }
        }
    }
}