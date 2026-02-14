package club.mcsports.lobby.listener

import club.mcsports.lobby.gui.player.GuiHotbar
import club.mcsports.lobby.util.LobbyScoreboard
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(private val lobbyScoreboard: LobbyScoreboard, private val hotbar: GuiHotbar) : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage(null)

        val uuid = event.player.uniqueId
        lobbyScoreboard.quit(uuid)
        hotbar.quit(uuid)
    }
}