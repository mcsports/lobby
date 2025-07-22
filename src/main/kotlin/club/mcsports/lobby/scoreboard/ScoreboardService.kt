package club.mcsports.lobby.scoreboard

import club.mcsports.lobby.Lobby
import club.mcsports.lobby.extension.*
import fr.mrmicky.fastboard.adventure.FastBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

class ScoreboardService(private val plugin: Lobby) {
    val scoreboardStorage = mutableMapOf<UUID, FastBoard>()

    fun create(player: Player): FastBoard {
        val scoreboard = scoreboardStorage[player.uniqueId]
            ?: FastBoard(player).also { scoreboardStorage[player.uniqueId] = it }
        scoreboard.updateTitle(miniMessage("<color:#bee7fa>\uD83C\uDFC5 <color:#58cbed>${"mcsports".toMiniFont()} <color:#bee7fa>\uD83C\uDFC5"))

        CoroutineScope(Dispatchers.IO).launch {
            val rankLine = player.getGroupPrefix() + "<color:${player.getGroupColor()}>" + (player.getGroupNameFormatted() ?: "<gray>no rank")
            scoreboard.updateLines(
                Component.empty(),
                miniMessage("<gray>${"Your Rank".toMiniFont()}"),
                miniMessage(rankLine),
                Component.empty(),
                miniMessage("<gray>${"Playtime".toMiniFont()}"),
                miniMessage(player.getPlayTime()),
                Component.empty()
            )
        }

        return scoreboard
    }

    fun quit(uuid: UUID) {
        scoreboardStorage.remove(uuid)
    }

    fun update() {
        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {

            CoroutineScope(Dispatchers.IO).launch {
                Bukkit.getOnlinePlayers().forEach { onlinePlayer ->

                    val scoreboard = scoreboardStorage[onlinePlayer.uniqueId] ?: create(onlinePlayer)

                    val rankLine = onlinePlayer.getGroupPrefix() + "<color:${onlinePlayer.getGroupColor()}>" + (onlinePlayer.getGroupNameFormatted() ?: "<gray>no rank")
                    scoreboard.updateLine(2, miniMessage(rankLine))
                    scoreboard.updateLine(5, miniMessage(onlinePlayer.getPlayTime()))

                }
            }

        }, 0L, 1L, TimeUnit.SECONDS)
    }
}