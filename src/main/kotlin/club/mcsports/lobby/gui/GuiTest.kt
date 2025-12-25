package club.mcsports.lobby.gui

import club.mcsports.lobby.extension.forEachInGridScissored
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.item.ItemComponents
import com.noxcrew.interfaces.InterfacesConstants
import com.noxcrew.interfaces.drawable.Drawable
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.CombinedInterface
import com.noxcrew.interfaces.interfaces.CombinedInterfaceBuilder
import com.noxcrew.interfaces.interfaces.buildCombinedInterface
import com.noxcrew.interfaces.properties.interfaceProperty
import com.noxcrew.interfaces.utilities.forEachInGrid
import com.noxcrew.interfaces.view.CombinedInterfaceView
import com.noxcrew.interfaces.view.InterfaceView
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.UUID

class GuiTest {

    private val selectionFrameDrawable =
        StaticElement(drawable(ItemComponents.SELECTION_FRAME.build()))

    fun open(player: Player, tab: Tab) {
        InterfacesConstants.SCOPE.launch {
            buildCombinedInterface {
                initialTitle = Component.empty()
                rows = 6

                setupNavbar(tab)
            }.open(player)
        }
    }
    
    private fun CombinedInterfaceBuilder.setupNavbar(tab: Tab) {
        val row = tab.row
        val column = tab.column

        withTransform { view, pane ->
            Tab.entries.forEach { entry ->
                view[entry.row - 1, entry.column] = StaticElement(entry.drawable) {
                    if(entry != tab) open(pane.player, entry)
                }
            }

            view[row, column] = selectionFrameDrawable
        }
    }
    enum class Tab(val row: Int, val column: Int, val drawable: Drawable) {
        PROFILE(8, 2, drawable(ItemComponents.PROFILE.build(false))),
        PARTY(8, 3, drawable(ItemComponents.MANAGE_PARTY.build(false))),
        FRIENDS(8, 4, drawable(ItemComponents.FRIENDS.build(false))),
        GYM_BAG(8, 5, drawable(ItemComponents.GYM_BAG.build(false))),
        SETTINGS(8, 6, drawable(ItemComponents.PADLOCK_LOCKED.build(false)));
    }
}