package club.mcsports.lobby.listener

import club.mcsports.lobby.scoreboard.ScoreboardService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(val scoreboardService: ScoreboardService) : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage(null)
        scoreboardService.quit(event.player.uniqueId)
    }
}