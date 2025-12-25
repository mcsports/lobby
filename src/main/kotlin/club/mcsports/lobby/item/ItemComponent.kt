package club.mcsports.lobby.item

import club.mcsports.lobby.extension.toMaterial
import io.github.solid.resourcepack.api.link.ModelLink
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

data class ItemComponent(
    val component: Component,
    val lore: List<Component> = listOf(),
    val model: ModelLink? = null,
    val fallbackModel: ModelLink? = null,
    val material: Material = Material.FLOW_BANNER_PATTERN,
    val hideTooltip: Boolean = false,
) {

    fun build(forceFallback: Boolean = false): ItemStack {
        val itemStack = if(forceFallback) fallbackModel?.createItemStack() ?: ItemStack(material) else model?.createItemStack() ?: (fallbackModel?.createItemStack() ?: ItemStack(material))

        itemStack.editMeta { meta ->
            meta.displayName(this.component)
            meta.lore(this.lore)
            meta.persistentDataContainer.set(NamespacedKey("noxesium", "immovable"), PersistentDataType.BOOLEAN, true)
        }

        return itemStack
    }

    private fun ModelLink.createItemStack(): ItemStack {
        val itemStack = ItemStack(parent?.toMaterial() ?: material)
        itemStack.editMeta { meta ->
            itemModel?.let { itemModel ->
                meta.itemModel = NamespacedKey.fromString(itemModel.toString())
            }

            meta.isHideTooltip = hideTooltip

            predicates?.let { predicates ->
                predicates.filter { predicate -> predicate.key == "custom_model_data" }.toList()
                    .firstOrNull()?.second.toString().toIntOrNull()
                    ?.let { customModelData -> meta.setCustomModelData(customModelData) }
            }
        }

        return itemStack
    }
}