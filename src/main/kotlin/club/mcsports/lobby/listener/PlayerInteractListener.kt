package club.mcsports.lobby.listener

import club.mcsports.droplet.friends.api.FriendsApi
import club.mcsports.droplet.party.api.PartyApi
import club.mcsports.lobby.extension.interaction
import club.mcsports.lobby.gui.menu.GuiGameSelector
import club.mcsports.lobby.gui.menu.GuiProfile
import club.mcsports.lobby.util.ItemInteraction
import com.noxcrew.interfaces.InterfacesConstants
import kotlinx.coroutines.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractListener(
    private val gameSelector: GuiGameSelector,
    private val partyApi: PartyApi.Future,
    private val friendsApi: FriendsApi.Future
) : Listener {

    private val testGui = GuiProfile(partyApi, friendsApi)

    @EventHandler
    fun handlePlayerInteract(event: PlayerInteractEvent) {
        val interaction = event.item?.interaction ?: return

        when (interaction) {
            ItemInteraction.OPEN_GAME_SELECTOR -> {
                InterfacesConstants.SCOPE.launch {
                    gameSelector.gui.open(event.player)
                }
            }

            ItemInteraction.OPEN_PROFILE -> {
                testGui.open(event.player)
            }

            else -> return
        }
    }
}