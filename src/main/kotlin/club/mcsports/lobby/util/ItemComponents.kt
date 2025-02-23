package club.mcsports.lobby.util

import club.mcsports.generated.PackBindings
import io.github.solid.binder.api.PackModel
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class ItemComponents(val component: Component, val lore: List<Component> = emptyList(), val model: PackModel? = null, val material: Material = Material.PAPER) {

    GAME_SELECTOR(miniMessage().deserialize("Game Selector"), material = Material.COMPASS),
    GYM_BAG(miniMessage().deserialize("Gym Bag"), material = Material.CAULDRON),
    PROFILE(miniMessage().deserialize("Profile"), material = Material.PLAYER_HEAD),
    PARTY_INVITE(miniMessage().deserialize("Invite Player"), model = PackBindings.RESET_ITEM.model),
    PARTY_MEMBER(miniMessage().deserialize("<player_name>"), material = Material.PLAYER_HEAD),
    ARROW_LEFT(miniMessage().deserialize("Previous"), material = Material.ARROW),
    ARROW_RIGHT(miniMessage().deserialize("Next"), material = Material.ARROW);

    fun build(): ItemStack {
        val itemStack = ItemStack(this.material)

        itemStack.editMeta { meta ->
            meta.displayName(this.component)
            meta.lore(this.lore)

            model?.let {
                //meta.itemModel = NamespacedKey.fromString(it.key.toString())
                Bukkit.getServer().broadcastMessage("Setting model for ${this.name} to ${it.key}")

                it.predicates?.let { predicates ->
                    predicates.firstOrNull { itemPredicate -> itemPredicate.name() == "custom_model_data" }?.value().toString().toIntOrNull()?.let { customModelData ->  meta.setCustomModelData(customModelData) }
                }
            }
        }

        return itemStack
    }

}