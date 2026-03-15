package club.mcsports.lobby.gui.player

import club.mcsports.lobby.extension.interaction
import club.mcsports.lobby.item.HotbarItem
import club.mcsports.lobby.util.ItemInteraction
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildPlayerInterface
import com.noxcrew.interfaces.view.PlayerInterfaceView
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class GuiHotbar {

    private val interfaces = mutableMapOf<UUID, PlayerInterfaceView>()

    private val gui = buildPlayerInterface {
        preventClickingEmptySlots = true
        onlyCancelItemInteraction = true

        withTransform { pane, _ ->
            val gameSelector = HotbarItem.GAME_SELECTOR.build()
            gameSelector.interaction = ItemInteraction.OPEN_GAME_SELECTOR

            val gymBag = HotbarItem.GYM_BAG.build()
            gymBag.interaction = ItemInteraction.OPEN_GYM_BAG

            val profile = HotbarItem.PROFILE.build()
            profile.interaction = ItemInteraction.OPEN_PROFILE

            pane.hotbar[0] = StaticElement(drawable(gameSelector))
            pane.hotbar[1] = StaticElement(drawable(gymBag))
            pane.hotbar[8] = StaticElement(drawable(profile))

            val armor = StaticElement(drawable(ItemStack(Material.AIR)))
            pane.armor.helmet = armor
            pane.armor.chest = armor
            pane.armor.leggings = armor
            pane.armor.boots = armor
        }
    }

    suspend fun set(player: Player) = gui.open(player).also { interfaces[player.uniqueId] = it }
    fun quit(uuid: UUID) = interfaces.remove(uuid)

}