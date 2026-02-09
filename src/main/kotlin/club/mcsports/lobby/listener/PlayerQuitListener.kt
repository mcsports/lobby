package club.mcsports.lobby.listener

import club.mcsports.lobby.util.LobbyScoreboard
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(val lobbyScoreboard: LobbyScoreboard) : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage(null)
        lobbyScoreboard.quit(event.player.uniqueId)
    }
}