package club.mcsports.lobby.gui

import club.mcsports.lobby.extension.forEachInGridScissored
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.item.GameModeItemComponents
import club.mcsports.lobby.item.ItemComponents
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildCombinedInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiGameSelector {

    val gui = buildCombinedInterface {
        allowClickingOwnInventoryIfClickingEmptySlotsIsPrevented = false
        preventClickingEmptySlots = true
        initialTitle = miniMessage("<color:#58cbed>ɢᴀᴍᴇ sᴇʟᴇᴄᴛᴏʀ")
        rows = 6

        withTransform { pane, view ->

            val closeButtonDrawable = StaticElement(drawable(ItemComponents.CLOSE_MENU.build())) {
                CoroutineScope(Dispatchers.IO).launch {
                    view.close(InventoryCloseEvent.Reason.PLAYER)
                }
            }

            val gameModeDrawables = GameModeItemComponents.entries.map {
                StaticElement(drawable(it.build()))
            }

            var gameModeIndex = 0
            forEachInGridScissored(3, 4, 2, 6) { row, column ->
                if (gameModeDrawables.size < gameModeIndex) {
                    return@forEachInGridScissored
                }

                if (gameModeDrawables.size == gameModeIndex) {
                    pane[row, column] = closeButtonDrawable
                    return@forEachInGridScissored
                }

                pane[row, column] = gameModeDrawables[gameModeIndex]
                gameModeIndex++
            }

        }
    }

}