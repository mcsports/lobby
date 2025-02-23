package club.mcsports.lobby.listener

import club.mcsports.lobby.Lobby
import club.mcsports.lobby.util.ItemComponents
import com.noxcrew.interfaces.click.ClickHandler
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildPlayerInterface
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType

class PlayerJoinListener(val plugin: Lobby) : Listener {

    @EventHandler
    fun handlePlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        val playerInterface = buildPlayerInterface {
            onlyCancelItemInteraction = true
            prioritiseBlockInteractions = true

            addPreprocessor { cancel() }

            addPreprocessor { event ->
                if (event.type == ClickType.SWAP_OFFHAND) {
                    cancel()
                }
            }

            withTransform { pane, _ ->

                val gameSelector = ItemComponents.GAME_SELECTOR.build()
                gameSelector.editMeta { meta ->
                    meta.persistentDataContainer.set(NamespacedKey("mcsports", "lobby/action"), PersistentDataType.STRING, "open_game_selector")
                }

                val gymBag = ItemComponents.GYM_BAG.build()
                gameSelector.editMeta { meta ->
                    meta.persistentDataContainer.set(NamespacedKey("mcsports", "lobby/action"), PersistentDataType.STRING, "open_gym_bag")
                }

                val profile = ItemComponents.PROFILE.build()
                profile.editMeta(SkullMeta::class.java) { meta ->
                    meta.owningPlayer = player
                    meta.persistentDataContainer.set(NamespacedKey("mcsports", "lobby/action"), PersistentDataType.STRING, "open_profile")
                }

                val partyInvite = ItemComponents.PARTY_INVITE.build()
                partyInvite.editMeta { meta ->
                    meta.persistentDataContainer.set(NamespacedKey("mcsports", "lobby/action"), PersistentDataType.STRING, "open_")
                }

                pane.hotbar[0] = StaticElement(drawable(gameSelector))
                pane.hotbar[1] = StaticElement(drawable(gymBag))
                pane.hotbar[2] = StaticElement(drawable(profile))
                pane.hotbar[4] = StaticElement(drawable(partyInvite))

                val armor = ItemStack(Material.AIR)
                pane.armor.helmet = StaticElement(drawable(armor))
                pane.armor.chest = StaticElement(drawable(armor))
                pane.armor.leggings = StaticElement(drawable(armor))
                pane.armor.boots = StaticElement(drawable(armor))
            }
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            runBlocking { playerInterface.open(player) }
        })
    }
}