package club.mcsports.lobby.item

import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import io.github.solid.resourcepack.api.link.ModelLink
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

/**
 * General item components used in the lobby
 */
enum class GameModeItemComponents(
    val component: Component,
    val lore: List<Component> = emptyList(),
    val model: ModelLink? = null,
    val material: Material = Material.PAPER
) {

    POWER_GOLF(miniMessage("Power-Golf"), lore = listOf(
        miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Try your best to get the ball into"),
        miniMessage("<color:#bee7fa>the hole with the fewest strokes"),
    ), material = Material.FLOW_BANNER_PATTERN),

    GLIDE(miniMessage("Glide"), lore = listOf(
        miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Fly through the map and score"),
        miniMessage("<color:#bee7fa>the best time to win"),
    ), material = Material.FLOW_BANNER_PATTERN),

    BOWLING(miniMessage("Bowling"), lore = listOf(
        miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Knock down all the pins to win"),
        miniMessage("<color:#bee7fa>and get the best score"),
    ), material = Material.FLOW_BANNER_PATTERN),

    MASTER_CHEFS(miniMessage("Master-Chefs"), lore = listOf(
        miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Hurry up and give the customers"),
        miniMessage("<color:#bee7fa>their food in time to win"),
    ), material = Material.FLOW_BANNER_PATTERN),

    SPRINT(miniMessage("Sprint"), lore = listOf(
        miniMessage("<gray><italic>${"Click to queue".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Run through the map with insane speed"),
        miniMessage("<color:#bee7fa>and be the first to finish."),
    ), material = Material.FLOW_BANNER_PATTERN),

    BOAT_RUN(miniMessage("Boat-Run"), lore = listOf(
        miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
        miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
    ), material = Material.FLOW_BANNER_PATTERN),

    SUMO(miniMessage("Sumo"), lore = listOf(
        miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
        miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
    ), material = Material.FLOW_BANNER_PATTERN),

    POOL(miniMessage("Pool"), lore = listOf(
        miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
        miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
    ), material = Material.FLOW_BANNER_PATTERN),

    DODGEBALL(miniMessage("Dodgeball"), lore = listOf(
        miniMessage("<red><italic>${"Currently closed".toMiniFont()}"),
        miniMessage("<white>Online: <color:#bee7fa><online_player_count>"),
        Component.empty(),
        miniMessage("<color:#bee7fa>Lorem ipsum dolor sit amet,"),
        miniMessage("<color:#bee7fa>consectetur adipiscing elit."),
    ), material = Material.FLOW_BANNER_PATTERN);

    fun build(): ItemStack {
        val itemStack = ItemStack(this.material)

        itemStack.editMeta { meta ->
            meta.displayName(this.component)
            meta.lore(this.lore)

            model?.let {
                it.itemModel?.let { itemModel ->
                    meta.itemModel = NamespacedKey.fromString(itemModel.toString())
                }

                it.predicates?.let { predicates ->
                    predicates.filter { predicate -> predicate.key == "custom_model_data" }.toList()
                        .firstOrNull()?.second.toString().toIntOrNull()
                        ?.let { customModelData -> meta.setCustomModelData(customModelData) }
                }
            }
        }

        return itemStack
    }

}