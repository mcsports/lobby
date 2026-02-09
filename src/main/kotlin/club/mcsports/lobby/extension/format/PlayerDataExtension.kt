package club.mcsports.lobby.extension.format

import club.mcsports.lobby.Lobby.Companion.playerApiSingleton
import org.bukkit.entity.Player

suspend fun Player.getPlayTime(): String {
    return try {
        playerApiSingleton.getOnlinePlayer(uniqueId).getOnlineTime()
    } catch(_: Exception) {
        0L
    }.formatTime(true)
}