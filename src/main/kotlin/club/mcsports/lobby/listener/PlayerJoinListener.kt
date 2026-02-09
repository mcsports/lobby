package club.mcsports.lobby.listener

import club.mcsports.lobby.Lobby
import club.mcsports.lobby.config.Config
import club.mcsports.lobby.extension.gui.set
import club.mcsports.lobby.item.HotbarItem
import club.mcsports.lobby.item.LobbyItem
import club.mcsports.lobby.util.ItemInteraction
import club.mcsports.lobby.util.SpawnPoint
import club.mcsports.lobby.util.LobbyScoreboard
//import club.mcsports.lobby.transform.PartyPaginationTransformation
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildPlayerInterface
import com.noxcrew.interfaces.view.PlayerInterfaceView
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

class PlayerJoinListener(private val plugin: Lobby, private val config: Config, private val lobbyScoreboard: LobbyScoreboard) : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun handlePlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        event.joinMessage(null)

        lobbyScoreboard.create(player)

        player.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, -1, 0, true, false, false))
        Bukkit.getScheduler().runTaskAsynchronously(
            plugin,
            Runnable {
                runBlocking {
                    playerInterfaces[player.uniqueId] = playerInterface(player.uniqueId).open(player)
                }
            }
        )
        player.teleport(config.spawnPoints[SpawnPoint.CLUBHOUSE] ?: Bukkit.getWorlds().first().spawnLocation)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        playerInterfaces.remove(event.player.uniqueId)
    }

    companion object {

        @JvmStatic
        val playerInterfaces = mutableMapOf<UUID, PlayerInterfaceView>()

        @JvmStatic
        private fun playerInterface(uuid: UUID) = buildPlayerInterface {
            preventClickingEmptySlots = true
            onlyCancelItemInteraction = true
            fillMenuWithAir = true

            withTransform { pane, _ ->
                val actionKey = NamespacedKey("mcsports", "lobby/action")

                val gameSelector = HotbarItem.GAME_SELECTOR.build()
                gameSelector.editMeta { meta ->
                    meta.persistentDataContainer.set(
                        actionKey,
                        ItemInteraction.OPEN_GAME_SELECTOR
                    )
                }

                val gymBag = HotbarItem.GYM_BAG.build()

                gymBag.editMeta { meta ->
                    meta.persistentDataContainer.set(
                        actionKey,
                        ItemInteraction.OPEN_GYM_BAG
                    )
                }

                val profile = HotbarItem.PROFILE.build()
                profile.editMeta { meta ->
                    meta.persistentDataContainer.set(
                        actionKey,
                        ItemInteraction.OPEN_PROFILE
                    )
                }

                pane.hotbar[0] = StaticElement(drawable(gameSelector))
                pane.hotbar[1] = StaticElement(drawable(gymBag))
                pane.hotbar[8] = StaticElement(drawable(profile))

                val armor = StaticElement(drawable(ItemStack(Material.AIR)))
                pane.armor.helmet = armor
                pane.armor.chest = armor
                pane.armor.leggings = armor
                pane.armor.boots = armor
            }
        }
    }
}