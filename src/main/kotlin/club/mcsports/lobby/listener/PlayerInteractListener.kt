package club.mcsports.lobby.listener

import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.gui.GuiTest
import com.noxcrew.interfaces.InterfacesConstants
import kotlinx.coroutines.launch
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import java.util.*

class PlayerInteractListener(private val gameSelector: GuiGameSelector) : Listener {

    private val testGui = GuiTest()

    @EventHandler
    fun handlePlayerInteract(event: PlayerInteractEvent) {
        val meta = event.item?.itemMeta ?: return
        meta.persistentDataContainer.get(NamespacedKey("mcsports", "lobby/action"), PersistentDataType.STRING)?.let { itemClickType ->

            when (itemClickType) {
                "open_game_selector" -> {
                    InterfacesConstants.SCOPE.launch {
                        gameSelector.gui.open(event.player)
                    }
                }

                "open_party_menu" -> {
                    InterfacesConstants.SCOPE.launch {
                        testGui.open(event.player, GuiTest.Tab.PROFILE)
                    }
                }

                else -> return
            }
        }
    }
}