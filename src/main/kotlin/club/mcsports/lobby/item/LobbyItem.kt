package club.mcsports.lobby.item

import club.mcsports.generated.GenericPackBindings
import club.mcsports.generated.LobbyPackBindings
import club.mcsports.lobby.extension.format.miniMessage
import club.mcsports.lobby.extension.format.toMiniFont
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * General item components used in the lobby
 */
enum class LobbyItem(
    private val component: CustomItem,
) {
    ARROW_LEFT(CustomItem(miniMessage("<white>Previous"), model = GenericPackBindings.CHEVRON_LEFT_ITEM.model)),
    ARROW_RIGHT(CustomItem(miniMessage("<white>Next"), model = GenericPackBindings.CHEVRON_RIGHT_ITEM.model)),

    PADLOCK_LOCKED(CustomItem(miniMessage("<white>Padlock Locked"), model = GenericPackBindings.PADLOCK_LOCKED_ITEM.model)),
    PADLOCK_UNLOCKED(CustomItem(miniMessage("<white>Padlock Unlocked"), model = GenericPackBindings.PADLOCK_UNLOCKED_ITEM.model)),

    DOCUMENT_HOLDER(CustomItem(miniMessage("<white>Document Holder"), model = GenericPackBindings.DOCUMENT_HOLDER_ITEM.model)),
    TRASH_BIN(CustomItem(miniMessage("<white>Trash Bin"), model = GenericPackBindings.TRASH_BIN_ITEM.model)),

    SELECTION_FRAME(
        CustomItem(
            miniMessage(""),
            model = GenericPackBindings.SLOT_SELECTION_ITEM.model,
            hideTooltip = true
        )
    ),

    FRIENDS(
        CustomItem(
            miniMessage("<white>Friends"),
            model = LobbyPackBindings.FRIENDS_ITEM.model
        )
    ),

    CLOSE_MENU(CustomItem(miniMessage("<red>Close Menu"), model = GenericPackBindings.CLOSE_BUTTON_ITEM.model)),

    ;
    fun build(forceFallback: Boolean = false): ItemStack {
        return component.build(forceFallback)
    }

}