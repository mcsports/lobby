package club.mcsports.lobby.item

import club.mcsports.generated.GenericPackBindings
import club.mcsports.generated.LobbyPackBindings
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * General item components used in the lobby
 */
enum class ItemComponents(
    private val component: ItemComponent,
) {

    GAME_SELECTOR(
        ItemComponent(
            miniMessage("<white>Game Selector"),
            model = LobbyPackBindings.CHECKERED_FLAG_ITEM.model
        )
    ),
    GYM_BAG(ItemComponent(miniMessage("<white>Gym Bag"), model = LobbyPackBindings.GYM_BAG_ITEM.model)),
    PROFILE(ItemComponent(miniMessage("<white>Profile"), model = LobbyPackBindings.PROFILE_ITEM.model)),
    PARTY_INVITE(
        ItemComponent(
            miniMessage("<white>Invite Player"),
            model = LobbyPackBindings.INVITE_TO_PARTY_ITEM.model
        )
    ),
    PARTY_MEMBER(ItemComponent(miniMessage("<white><player_name>"), material = Material.PLAYER_HEAD)),
    CLOSE_MENU(ItemComponent(miniMessage("<red>Close Menu"), model = GenericPackBindings.CLOSE_BUTTON_ITEM.model)),
    LOBBY_SERVER(
        ItemComponent(
            miniMessage("<color:#58cbed>${"Lobby".toMiniFont()} <service_number>"), lore = listOf(
                miniMessage("<gray><italic>${"Click to connect".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>")
            ), model = LobbyPackBindings.LOBBY_ISLAND_ITEM.model
        )
    ),
    LOBBY_SERVER_UNAVAILABLE(
        ItemComponent(
            miniMessage("<color:#58cbed>${"Lobby".toMiniFont()} <service_number>"), lore = listOf(
                miniMessage("<red><italic>${"Currently on this server".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>")
            ), model = LobbyPackBindings.CURRENT_LOBBY_ISLAND_ITEM.model
        )
    ),
    CLUB_HOUSE(
        ItemComponent(
            miniMessage("Clubhouse"), lore = listOf(
                miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Drink, gamble, play pool, meet"),
                miniMessage("<color:#bee7fa>others - a perfectly normal clubhouse."),
            ),
            model = LobbyPackBindings.CLUBHOUSE_ITEM.model
        )
    ),
    POOL(
        ItemComponent(
            miniMessage("Pool"), lore = listOf(
                miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Play with others - and with your own rules."),
                miniMessage("<color:#bee7fa>Only at the clubhouse."),
            ),
            model = LobbyPackBindings.POOL_ICON_ITEM.model
        )
    ),
    ARROW_LEFT(ItemComponent(miniMessage("<white>Previous"), model = GenericPackBindings.CHEVRON_LEFT_ITEM.model)),
    ARROW_RIGHT(ItemComponent(miniMessage("<white>Next"), model = GenericPackBindings.CHEVRON_RIGHT_ITEM.model));

    fun build(): ItemStack {
        return component.build()
    }

}