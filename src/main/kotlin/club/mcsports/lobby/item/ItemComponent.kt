package club.mcsports.lobby.item

import club.mcsports.lobby.extension.toMaterial
import io.github.solid.resourcepack.api.link.ModelLink
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

data class ItemComponent(
    val component: Component,
    val lore: List<Component> = listOf(),
    val model: ModelLink? = null,
    val fallbackModel: ModelLink? = null,
    val material: Material = Material.FLOW_BANNER_PATTERN
) {

    fun build(): ItemStack {
        val itemStack = model?.createItemStack() ?: (fallbackModel?.createItemStack() ?: ItemStack(material))

        itemStack.editMeta { meta ->
            meta.displayName(this.component)
            meta.lore(this.lore)
        }

        return itemStack
    }

    private fun ModelLink.createItemStack(): ItemStack {
        val itemStack = ItemStack(parent?.toMaterial() ?: material)
        itemStack.editMeta { meta ->
            itemModel?.let { itemModel ->
                meta.itemModel = NamespacedKey.fromString(itemModel.toString())
            }

            predicates?.let { predicates ->
                predicates.filter { predicate -> predicate.key == "custom_model_data" }.toList()
                    .firstOrNull()?.second.toString().toIntOrNull()
                    ?.let { customModelData -> meta.setCustomModelData(customModelData) }
            }
        }

        return itemStack
    }
}