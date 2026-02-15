package club.mcsports.lobby.util

import club.mcsports.lobby.Lobby
import club.mcsports.lobby.extension.formattedRank
import club.mcsports.lobby.extension.format.getPlayTime
import club.mcsports.lobby.extension.format.miniMessage
import club.mcsports.lobby.extension.format.toMiniFont
import fr.mrmicky.fastboard.adventure.FastBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.TimeUnit

class LobbyScoreboard(private val plugin: Lobby) {
    val scoreboardStorage = mutableMapOf<UUID, FastBoard>()

    fun create(player: Player): FastBoard {
        val scoreboard = scoreboardStorage[player.uniqueId]
            ?: FastBoard(player).also { scoreboardStorage[player.uniqueId] = it }

        scoreboard.updateTitle(miniMessage("<color:#bee7fa>\uD83C\uDFC5 <color:#58cbed>${"mcsports".toMiniFont()} <color:#bee7fa>\uD83C\uDFC5"))

        CoroutineScope(Dispatchers.IO).launch {
            scoreboard.updateLines(
                Component.empty(),
                miniMessage("<gray>${"Your Rank".toMiniFont()}"),
                miniMessage(player.formattedRank),
                Component.empty(),
                miniMessage("<gray>${"Playtime".toMiniFont()}"),
                miniMessage(player.getPlayTime()),
                Component.empty()
            )
        }

        return scoreboard
    }

    fun quit(uuid: UUID) = scoreboardStorage.remove(uuid)?.delete()

    fun update() {
        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {

            CoroutineScope(Dispatchers.IO).launch {
                Bukkit.getOnlinePlayers().forEach { onlinePlayer ->

                    val scoreboard = scoreboardStorage[onlinePlayer.uniqueId] ?: create(onlinePlayer)

                    scoreboard.updateLine(2, miniMessage(onlinePlayer.formattedRank))
                    scoreboard.updateLine(5, miniMessage(onlinePlayer.getPlayTime()))
                }
            }

        }, 0L, 1L, TimeUnit.SECONDS)
    }
}