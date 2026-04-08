package club.mcsports.lobby.extension.format

import club.mcsports.lobby.Lobby.Companion.playerApiSingleton
import kotlinx.coroutines.future.await
import org.bukkit.entity.Player

suspend fun Player.getPlayTime(): String {
    return try {
        playerApiSingleton.getOnlinePlayer(uniqueId).await().getOnlineTime()
    } catch(_: Exception) {
        0L
    }.formatTime(true)
}