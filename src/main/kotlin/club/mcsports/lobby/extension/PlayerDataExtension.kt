package club.mcsports.lobby.extension

import club.mcsports.lobby.Lobby.Companion.playerApiSingleton
import net.luckperms.api.LuckPerms
import net.luckperms.api.node.NodeType
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun Player.getGroupName(): String? {
    return Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)?.provider?.let { luckPerms ->
        val groupName = luckPerms.userManager.getUser(uniqueId)?.primaryGroup

        groupName
    }
}

fun Player.getGroupNameFormatted(): String? {
    return Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)?.provider?.let { luckPerms ->
        val groupName = luckPerms.userManager.getUser(uniqueId)?.primaryGroup

        val replaceFirstChar = groupName?.lowercase()?.replaceFirstChar { it.uppercase() } ?: return@let null
        replaceFirstChar
    }
}

fun Player.getGroupPrefix(): String? {
    return Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)?.provider?.let { luckPerms ->
        val groupName = luckPerms.userManager.getUser(uniqueId)?.primaryGroup

        val group = luckPerms.groupManager.getGroup(groupName ?: return@let null)
            ?: return@let null

        val prefix = group.cachedData.metaData.prefix
            ?: group.getNodes(NodeType.PREFIX).maxByOrNull { it.priority }?.metaValue ?: return@let null

        prefix
    }
}

fun Player.getGroupColor(): String? {
    return Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)?.provider?.let { luckPerms ->
        val groupName = luckPerms.userManager.getUser(uniqueId)?.primaryGroup

        val group = luckPerms.groupManager.getGroup(groupName ?: return@let null)
            ?: return@let null

        val groupColor = group.cachedData.metaData.getMetaValue("color")
            ?: group.getNodes(NodeType.META)
                .firstOrNull { it.metaKey.equals("color", ignoreCase = true) }
                ?.metaValue

        groupColor
    }
}

suspend fun Player.getPlayTime(): String {
    return try {
        playerApiSingleton.getOnlinePlayer(uniqueId).getOnlineTime()
    } catch(_: Exception) {
        0L
    }.formatTime(true)
}