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
enum class GameModeItemComponents(val component: Component, val lore: List<Component> = emptyList(), val model: PackModel? = null, val material: Material = Material.PAPER) {

    POWER_GOLF(miniMessage("Power-Golf"), material = Material.FLOW_BANNER_PATTERN),
    GLIDE(miniMessage("Glide"), material = Material.FLOW_BANNER_PATTERN),
    BOWLING(miniMessage("Bowling"), material = Material.FLOW_BANNER_PATTERN),
    MASTER_CHEFS(miniMessage("Master-Chefs"), material = Material.FLOW_BANNER_PATTERN),
    SPRINT(miniMessage("Sprint"), material = Material.FLOW_BANNER_PATTERN),
    BOAT_RUN(miniMessage("Boat-Run"), material = Material.FLOW_BANNER_PATTERN),
    SUMO(miniMessage("Sumo"), material = Material.FLOW_BANNER_PATTERN),
    POOL(miniMessage("Pool"), material = Material.FLOW_BANNER_PATTERN),
    DODGEBALL(miniMessage("Dodgeball"), material = Material.FLOW_BANNER_PATTERN);

    fun build(): ItemStack {
        val itemStack = ItemStack(this.material)

        itemStack.editMeta { meta ->
            meta.displayName(this.component)
            meta.lore(this.lore)

            model?.let {
                meta.itemModel = NamespacedKey.fromString(it.key.toString())

                it.predicates?.let { predicates ->
                    predicates.firstOrNull { itemPredicate -> itemPredicate.name() == "custom_model_data" }?.value().toString().toIntOrNull()?.let { customModelData ->  meta.setCustomModelData(customModelData) }
                }
            }
        }

        return itemStack
    }

}