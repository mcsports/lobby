package club.mcsports.lobby.item

import club.mcsports.generated.PackBindings
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
enum class ItemComponents(
    val component: Component,
    val lore: List<Component> = emptyList(),
    val model: ModelLink? = null,
    val material: Material = Material.FLOW_BANNER_PATTERN,
) {

    GAME_SELECTOR(miniMessage("<white>Game Selector"), material = Material.COMPASS),
    GYM_BAG(miniMessage("<white>Gym Bag"), model = PackBindings.GYM_BAG_ITEM.model),
    PROFILE(miniMessage("<white>Profile"), model = PackBindings.PROFILE_ITEM.model),
    PARTY_INVITE(miniMessage("<white>Invite Player"), model = PackBindings.INVITE_TO_PARTY_ITEM.model),
    PARTY_MEMBER(miniMessage("<white><player_name>"), material = Material.PLAYER_HEAD),
    CLOSE_MENU(miniMessage("<red>Close Menu"), material = Material.BARRIER),
    LOBBY_SERVER(
        miniMessage("<color:#58cbed>${"Lobby".toMiniFont()} <service_number>"), lore = listOf(
            miniMessage("<gray><italic>${"Click to connect".toMiniFont()}"),
            miniMessage("<white>Online: <color:#bee7fa><online_player_count>")
        ), model = PackBindings.LOBBY_ISLAND_ITEM.model
    ),
    LOBBY_SERVER_UNAVAILABLE(
        miniMessage("<color:#58cbed>${"Lobby".toMiniFont()} <service_number>"), lore = listOf(
            miniMessage("<red><italic>${"Currently on this server".toMiniFont()}"),
            miniMessage("<white>Online: <color:#bee7fa><online_player_count>")
        ), model = PackBindings.CURRENT_LOBBY_ISLAND_ITEM.model
    ),
    ARROW_LEFT(miniMessage("<white>Previous"), model = PackBindings.CHEVRON_LEFT_ITEM.model),
    ARROW_RIGHT(miniMessage("<white>Next"), model = PackBindings.CHEVRON_RIGHT_ITEM.model), ;

    fun build(): ItemStack {
        val itemStack = ItemStack(this.material)

        itemStack.editMeta { meta ->
            meta.displayName(this.component)
            meta.lore(this.lore)

            model?.let { model ->
                model.itemModel?.let { itemModel ->
                    meta.itemModel = NamespacedKey.fromString(itemModel.toString())
                }

                model.predicates?.let { predicates ->
                    predicates.filter { predicate -> predicate.key == "custom_model_data" }.toList()
                        .firstOrNull()?.second.toString().toIntOrNull()
                        ?.let { customModelData -> meta.setCustomModelData(customModelData) }
                }
            }
        }

        return itemStack
    }

}