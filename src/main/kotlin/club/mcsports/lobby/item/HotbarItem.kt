package club.mcsports.lobby.item

import club.mcsports.generated.GenericPackBindings
import club.mcsports.generated.LobbyPackBindings
import club.mcsports.lobby.extension.format.miniMessage
import org.bukkit.inventory.ItemStack

enum class HotbarItem(
    private val component: CustomItem,
) {

    GAME_SELECTOR(CustomItem(miniMessage("<white>Game Selector"), model = LobbyPackBindings.CHECKERED_FLAG_ITEM.model)),
    GYM_BAG(CustomItem(miniMessage("<white>Gym Bag"), model = LobbyPackBindings.GYM_BAG_ITEM.model)),
    PROFILE(CustomItem(miniMessage("<white>Profile"), model = LobbyPackBindings.PROFILE_ITEM.model));

    fun build(forceFallback: Boolean = false): ItemStack {
        return component.build(forceFallback)
    }
}