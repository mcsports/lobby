package club.mcsports.lobby.item

import club.mcsports.generated.LobbyPackBindings
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

enum class PartyItemComponents(
    private val component: ItemComponent,
) {

    PARTY_INVITES(
        ItemComponent(
            miniMessage("<white>Party Invites"),
            model = LobbyPackBindings.INVITE_TO_PARTY_ITEM.model,
            lore = listOf(miniMessage("<gray>${"Click to toggle".toMiniFont()}"), Component.empty())
        )
    )

    ;

    fun build(forceFallback: Boolean = false): ItemStack {
        return component.build(forceFallback)
    }

}