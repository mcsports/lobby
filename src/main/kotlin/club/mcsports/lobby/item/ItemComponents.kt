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
    val material: Material = Material.PAPER,
) {

    GAME_SELECTOR(miniMessage("<white>Game Selector"), material = Material.COMPASS),
    GYM_BAG(miniMessage("<white>Gym Bag"), material = Material.CAULDRON),
    PROFILE(miniMessage("<white>Profile"), material = Material.PLAYER_HEAD),
    PARTY_INVITE(miniMessage("<white>Invite Player"), model = PackBindings.INVITE_TO_PARTY_ITEM.model),
    PARTY_MEMBER(miniMessage("<white><player_name>"), material = Material.PLAYER_HEAD),
    CLOSE_MENU(miniMessage("<red>Close Menu"), material = Material.BARRIER),
    LOBBY_SERVER(
        miniMessage("<color:#58cbed>Lobby <service_number>"), lore = listOf(
            miniMessage("<gray><italic>${"Click to connect".toMiniFont()}"),
            Component.empty(),
            miniMessage("<dark_gray><strikethrough>${"-".repeat(15)}"),
            miniMessage("<white>Online: <color:#bee7fa><online_player_count>")
        ), material = Material.FLOW_BANNER_PATTERN
    ),
    LOBBY_SERVER_UNAVAILABLE(
        miniMessage("<color:#58cbed>${"Lobby <service_number>".toMiniFont()}"), lore = listOf(
            miniMessage("<red>${"Currently on this server".toMiniFont()}"),
            Component.empty(),
            miniMessage("<dark_gray><strikethrough>${"-".repeat(15)}"),
            miniMessage("<white>Online: <color:#bee7fa><online_player_count>")
        ), material = Material.FLOW_BANNER_PATTERN
    ),
    ARROW_LEFT(miniMessage("<white>Previous"), material = Material.ARROW);

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