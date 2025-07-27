package club.mcsports.lobby.item

import club.mcsports.generated.LobbyPackBindings
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

/**
 * General item components used in the lobby
 */
enum class GameModeItemComponents(
    private val component: ItemComponent,
) {

    POWER_GOLF(
        ItemComponent(
            miniMessage("Power-Golf"), lore = listOf(
                miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Try your best to get the ball into"),
                miniMessage("<color:#bee7fa>the hole with the fewest strokes"),
            ),
            model = LobbyPackBindings.GOLF_ICON_ITEM.model
        ),
    ),

    GLIDE(
        ItemComponent(
            miniMessage("Glide"), lore = listOf(
                miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Fly through the map and score"),
                miniMessage("<color:#bee7fa>the best time to win"),
            ),
            model = LobbyPackBindings.GLIDE_ICON_ITEM.model
        ),
    ),

    BOWLING(
        ItemComponent(
            miniMessage("Bowling"), lore = listOf(
                miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Knock down all the pins to win"),
                miniMessage("<color:#bee7fa>and get the best score"),
            ),
            model = LobbyPackBindings.BOWLING_ICON_ITEM.model
        ),
    ),

    MASTER_CHEFS(
        ItemComponent(
            miniMessage("Master-Chefs"), lore = listOf(
                miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Hurry up and give the customers"),
                miniMessage("<color:#bee7fa>their food in time to win"),
            ),
            model = LobbyPackBindings.MASTERCHEFS_ICON_ITEM.model
        ),
    ),

    SPRINT(
        ItemComponent(
            miniMessage("Sprint"), lore = listOf(
                miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Run through the map with insane speed"),
                miniMessage("<color:#bee7fa>and be the first to finish."),
            ),
            model = LobbyPackBindings.SPRINT_ICON_ITEM.model
        ),
    ),

    BOAT_RUN(
        ItemComponent(
            miniMessage("Boat-Run"), lore = listOf(
                miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
                miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
            )
        ),
    ),

    SUMO(
        ItemComponent(
            miniMessage("Sumo"), lore = listOf(
                miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
                miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
            )
        ),
    ),

    DODGEBALL(
        ItemComponent(
            miniMessage("Dodgeball"), lore = listOf(
                miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
                miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
                Component.empty(),
                miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
                miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
            )
        ),
    );

    fun build(): ItemStack {
        return component.build()
    }

}