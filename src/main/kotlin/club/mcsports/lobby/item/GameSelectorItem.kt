package club.mcsports.lobby.item

import club.mcsports.generated.LobbyPackBindings
import club.mcsports.lobby.extension.format.miniMessage
import club.mcsports.lobby.extension.format.toMiniFont
import net.kyori.adventure.text.Component

/**
 * Items used in the game selector
 */
enum class GameSelectorItem(
    private val component: CustomItem,
) {

    POWER_GOLF(
        CustomItem(
            miniMessage("Power-Golf"), lore = listOf(
                miniMessage("<click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Try your best to get the ball into"),
                miniMessage("<color:#bee7fa>the hole with the fewest strokes"),
            ),
            model = LobbyPackBindings.GOLF_ICON_GAME_ITEM.model,
            fallbackModel = LobbyPackBindings.GOLF_ICON_GRAYED_OUT_GAME_ITEM.model,
        ),
    ),

    GLIDE(
        CustomItem(
            miniMessage("Glide"), lore = listOf(
                miniMessage("<click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Fly through the map and score"),
                miniMessage("<color:#bee7fa>the best time to win"),
            ),
            model = LobbyPackBindings.GLIDE_ICON_GAME_ITEM.model,
            fallbackModel = LobbyPackBindings.GLIDE_ICON_GRAYED_OUT_GAME_ITEM.model
        ),
    ),

    BOWLING(
        CustomItem(
            miniMessage("Bowling"), lore = listOf(
                miniMessage("<click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Knock down all the pins to win"),
                miniMessage("<color:#bee7fa>and get the best score"),
            ),
            model = LobbyPackBindings.BOWLING_ICON_GAME_ITEM.model,
            fallbackModel = LobbyPackBindings.BOWLING_ICON_GRAYED_OUT_GAME_ITEM.model
        ),
    ),

    MASTER_CHEFS(
        CustomItem(
            miniMessage("Master-Chefs"), lore = listOf(
                miniMessage("<click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Hurry up and give the customers"),
                miniMessage("<color:#bee7fa>their food in time to win"),
            ),
            model = LobbyPackBindings.MASTERCHEFS_ICON_GAME_ITEM.model,
            fallbackModel = LobbyPackBindings.MASTERCHEFS_ICON_GRAYED_OUT_GAME_ITEM.model
        ),
    ),

    SPRINT(
        CustomItem(
            miniMessage("Sprint"), lore = listOf(
                miniMessage("<click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Run through the map with insane speed"),
                miniMessage("<color:#bee7fa>and be the first to finish."),
            ),
            model = LobbyPackBindings.SPRINT_ICON_GAME_ITEM.model,
            fallbackModel = LobbyPackBindings.SPRINT_ICON_GRAYED_OUT_GAME_ITEM.model
        ),
    ),

    BOAT_RUN(
        CustomItem(
            miniMessage("Boat-Run"), lore = listOf(
                miniMessage("<red><italic><click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
                miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
            )
        ),
    ),

    SUMO(
        CustomItem(
            miniMessage("Sumo"), lore = listOf(
                miniMessage("<red><italic><click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
                miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
            )
        ),
    ),

    DODGEBALL(
        CustomItem(
            miniMessage("Dodgeball"), lore = listOf(
                miniMessage("<red><italic><click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
                miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
            )
        ),
    ),

    CLUB_HOUSE(
        CustomItem(
            miniMessage("Clubhouse"), lore = listOf(
                miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Drink, gamble, play pool, meet"),
                miniMessage("<color:#bee7fa>others - a perfectly normal clubhouse."),
            ),
            model = LobbyPackBindings.CLUBHOUSE_GRAYED_OUT_GAME_ITEM.model //TODO: Update item because it's permanently closed atm
        )
    ),

    POOL(
        CustomItem(
            miniMessage("Pool"), lore = listOf(
                miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Play with others - and with your own rules."),
                miniMessage("<color:#bee7fa>Only at the clubhouse."),
            ),
            model = LobbyPackBindings.POOL_ICON_GRAYED_OUT_GAME_ITEM.model //TODO: Update item because it's permanently closed atm
        )
    ),

    LOBBY_SERVER(
        CustomItem(
            miniMessage("Lobby <service_number>"), lore = listOf(
                miniMessage("<click_action>"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>")
            ), model = LobbyPackBindings.LOBBY_ISLAND_ITEM.model, fallbackModel = LobbyPackBindings.CURRENT_LOBBY_ISLAND_ITEM.model
        )
    ),

    ;
    fun build(forceFallback: Boolean = false) = component.build(forceFallback)
}