package club.mcsports.lobby.listener

import club.mcsports.lobby.Lobby
import club.mcsports.lobby.config.Config
import club.mcsports.lobby.gui.player.GuiHotbar
import club.mcsports.lobby.util.SpawnPoint
import club.mcsports.lobby.util.LobbyScoreboard
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PlayerJoinListener(
    private val plugin: Lobby,
    private val config: Config,
    private val lobbyScoreboard: LobbyScoreboard,
    private val hotbar: GuiHotbar,
) : Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    fun handlePlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        event.joinMessage(null)

        lobbyScoreboard.create(player)

        Bukkit.getScheduler().runTaskAsynchronously(
            plugin,
            Runnable {
                runBlocking {
                    hotbar.gui.open(player)
                }
            }
        )

        player.teleport(config.spawnPoints[SpawnPoint.CLUBHOUSE] ?: Bukkit.getWorlds().first().spawnLocation)
        player.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, -1, 0, true, false, false))
    }

}