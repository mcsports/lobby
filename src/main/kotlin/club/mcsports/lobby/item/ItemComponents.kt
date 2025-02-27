package club.mcsports.lobby.item

import club.mcsports.generated.PackBindings
import club.mcsports.lobby.extension.miniMessage
import io.github.solid.binder.api.PackModel
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

/**
 * General item components used in the lobby
 */
enum class ItemComponents(val component: Component, val lore: List<Component> = emptyList(), val model: PackModel? = null, val material: Material = Material.PAPER) {

    GAME_SELECTOR(miniMessage("<white>Game Selector"), material = Material.COMPASS),
    GYM_BAG(miniMessage("<white>Gym Bag"), material = Material.CAULDRON),
    PROFILE(miniMessage("<white>Profile"), material = Material.PLAYER_HEAD),
    PARTY_INVITE(miniMessage("<white>Invite Player"), model = PackBindings.RESET_ITEM.model),
    PARTY_MEMBER(miniMessage("<white><player_name>"), material = Material.PLAYER_HEAD),
    CLOSE_MENU(miniMessage("<red>Close Menu"), material = Material.BARRIER),
    ARROW_LEFT(miniMessage("<white>Previous"), material = Material.ARROW);

    fun build(): ItemStack {
        val itemStack = ItemStack(this.material)

        itemStack.editMeta { meta ->
            meta.displayName(this.component)
            meta.lore(this.lore)

            model?.let {
                meta.itemModel = NamespacedKey.fromString(it.key.toString())
                Bukkit.getServer().broadcastMessage("Setting model for ${this.name} to ${it.key}")

                it.predicates?.let { predicates ->
                    predicates.firstOrNull { itemPredicate -> itemPredicate.name() == "custom_model_data" }?.value().toString().toIntOrNull()?.let { customModelData ->  meta.setCustomModelData(customModelData) }
                }
            }
        }

        return itemStack
    }

}