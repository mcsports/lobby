package club.mcsports.lobby.listener

import app.simplecloud.droplet.player.api.PlayerApi
import club.mcsports.lobby.Lobby
import club.mcsports.lobby.config.Config
import club.mcsports.lobby.extension.extendBottom
import club.mcsports.lobby.extension.formatTime
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import club.mcsports.lobby.item.ItemComponents
import club.mcsports.lobby.location.SpawnPoint
import club.mcsports.lobby.scoreboard.ScoreboardService
import club.mcsports.lobby.transform.PartyPaginationTransformation
import club.mcsports.lobby.util.Party
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.grid.GridPoint
import com.noxcrew.interfaces.interfaces.buildPlayerInterface
import com.noxcrew.interfaces.transform.builtin.PaginationButton
import com.noxcrew.interfaces.view.PlayerInterfaceView
import fr.mrmicky.fastboard.adventure.FastBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.text.Component
import net.luckperms.api.LuckPerms
import net.luckperms.api.node.NodeType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

class PlayerJoinListener(private val plugin: Lobby, private val config: Config, private val scoreboardService: ScoreboardService) : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun handlePlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        event.joinMessage(null)

        scoreboardService.create(player)

        player.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, -1, 0, true, false, true))
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

        val parties = mutableMapOf<UUID, Party>()

        @JvmStatic
        val playerInterfaces = mutableMapOf<UUID, PlayerInterfaceView>()

        @JvmStatic
        private fun playerInterface(uuid: UUID) = buildPlayerInterface {
            preventClickingEmptySlots = true
            onlyCancelItemInteraction = true
            fillMenuWithAir = true

            val itemPrev = ItemComponents.ARROW_LEFT.build()
            val itemNext = ItemComponents.ARROW_RIGHT.build()
            val previous = drawable(itemPrev)
            val next = drawable(itemNext)
            val previousButton =
                PaginationButton(GridPoint.at(3, 5), previous, mapOf(ClickType.LEFT to -1, ClickType.RIGHT to -1))
            val nextButton =
                PaginationButton(GridPoint.at(3, 8), next, mapOf(ClickType.LEFT to 1, ClickType.RIGHT to 1))
            val party = parties[uuid] ?: Party().also { parties[uuid] = it }

            addTransform(PartyPaginationTransformation(party, previousButton, nextButton))

            withTransform { pane, _ ->

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
                profile.editMeta { meta ->
                    meta.persistentDataContainer.set(
                        NamespacedKey("mcsports", "lobby/action"),
                        PersistentDataType.STRING,
                        "open_profile"
                    )
                }

                val manageParty = ItemComponents.MANAGE_PARTY.build()
                manageParty.editMeta { meta ->
                    meta.persistentDataContainer.set(
                        NamespacedKey("mcsports", "lobby/action"),
                        PersistentDataType.STRING,
                        "open_party_menu"
                    )
                }
                pane.hotbar[0] = StaticElement(drawable(gameSelector))
                pane.hotbar[1] = StaticElement(drawable(gymBag))
                pane.hotbar[2] = StaticElement(drawable(profile))
                pane.hotbar[4] = StaticElement(drawable(manageParty))

                val armor = ItemStack(Material.AIR)
                pane.armor.helmet = StaticElement(drawable(armor))
                pane.armor.chest = StaticElement(drawable(armor))
                pane.armor.leggings = StaticElement(drawable(armor))
                pane.armor.boots = StaticElement(drawable(armor))
            }
        }
    }
}