package club.mcsports.lobby.gui

import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.item.ItemComponents
import club.mcsports.lobby.util.Party
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildCombinedInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiInvitePlayers(party: Party) {

    val invites = mutableListOf<String>()
    private val nameRegex = "[a-zA-Z0-9_]{3,16}".toRegex()

    val gui = buildCombinedInterface {

        allowClickingOwnInventoryIfClickingEmptySlotsIsPrevented = false
        preventClickingEmptySlots = true
        initialTitle = miniMessage("<white>Invite Players")

        rows = 5

        withTransform { pane, view ->

            pane[0, 8] = StaticElement(drawable(ItemComponents.CLOSE_MENU.build())) {
                CoroutineScope(Dispatchers.IO).launch {
                    view.close(InventoryCloseEvent.Reason.PLAYER)
                }
            }

        }

    }

}