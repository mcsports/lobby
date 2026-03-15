package club.mcsports.lobby.item

import club.mcsports.generated.GenericPackBindings
import club.mcsports.generated.LobbyPackBindings
import club.mcsports.lobby.extension.format.miniMessage
import club.mcsports.lobby.extension.format.toMiniFont
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

enum class ProfileTabItem(
    private val component: CustomItem,
) {

    FRIENDS(
        CustomItem(
            miniMessage("<aqua>Friends"),
            listOf(
                miniMessage("<gray><friend_requests> ${"Requests".toMiniFont()}"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Manage your friends"),
                miniMessage("<color:#bee7fa>or make some new ones!")
            ),
            model = LobbyPackBindings.FRIENDS_ITEM.model
        )
    ),

    PARTY(
        CustomItem(
            miniMessage("<aqua>Party"),
            listOf(
                miniMessage("<party_state>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Throw a party"),
                miniMessage("<color:#bee7fa>with your friends!"),
                miniMessage("<color:#bee7fa>Well, if you have any")
            ),
            model = LobbyPackBindings.BALLOONS_ITEM.model,
        )
    ),

    SETTINGS(
        CustomItem(
            miniMessage("<aqua>Settings"),
            listOf(
                miniMessage("<gray>${"Click to manage"}"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Choose options"),
                miniMessage("<color:#bee7fa>for your in-game"),
                miniMessage("<color:#bee7fa>appearance.")
            ),
            model = GenericPackBindings.COG_ITEM.model
        )
    )

    ;fun build(forceFallback: Boolean = false): ItemStack {
        return component.build(forceFallback)
    }
}