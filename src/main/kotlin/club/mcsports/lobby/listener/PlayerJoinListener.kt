package club.mcsports.lobby.listener

import club.mcsports.lobby.Lobby
import club.mcsports.lobby.item.ItemComponents
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildPlayerInterface
import com.noxcrew.interfaces.view.PlayerInterfaceView
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PlayerJoinListener(private val plugin: Lobby) : Listener {

    @EventHandler
    fun handlePlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        event.joinMessage(null)

        player.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, -1, 0, true, false, true))
        Bukkit.getScheduler().runTaskAsynchronously(
            plugin,
            Runnable {
                runBlocking {
                    playerInterfaces[player.uniqueId.toString()] = playerInterface.open(player)
                }
            }
        )
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        playerInterfaces.remove(event.player.uniqueId.toString())
    }

    companion object {

        @JvmStatic
        private val playerInterfaces = mutableMapOf<String, PlayerInterfaceView>()

        @JvmStatic
        private val playerInterface = buildPlayerInterface {
            preventClickingEmptySlots = true
            prioritiseBlockInteractions = true
            onlyCancelItemInteraction = true
            fillMenuWithAir = true


            withTransform { pane, view ->

                val gameSelector = ItemComponents.GAME_SELECTOR.build()
                gameSelector.editMeta { meta ->
                    meta.persistentDataContainer.set(
                        NamespacedKey("mcsports", "lobby/action"),
                        PersistentDataType.STRING,
                        "open_game_selector"
                    )
                }

                val gymBag = ItemComponents.GYM_BAG.build()
                gymBag.editMeta { meta ->
                    meta.persistentDataContainer.set(
                        NamespacedKey("mcsports", "lobby/action"),
                        PersistentDataType.STRING,
                        "open_gym_bag"
                    )
                }

                val profile = ItemComponents.PROFILE.build()
                profile.editMeta(SkullMeta::class.java) { meta ->
                    meta.owningPlayer = view.player
                    meta.persistentDataContainer.set(
                        NamespacedKey("mcsports", "lobby/action"),
                        PersistentDataType.STRING,
                        "open_profile"
                    )
                }

                val partyInvite = ItemComponents.PARTY_INVITE.build()
                partyInvite.editMeta { meta ->
                    meta.persistentDataContainer.set(
                        NamespacedKey("mcsports", "lobby/action"),
                        PersistentDataType.STRING,
                        "open_"
                    )
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
    }
}