package club.mcsports.lobby.extension

import net.luckperms.api.LuckPerms
import net.luckperms.api.node.NodeType
import org.bukkit.Bukkit
import org.bukkit.entity.Player

private val luckPerms = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)?.provider

val Player.groupName: String
    get() = luckPerms?.userManager?.getUser(uniqueId)?.primaryGroup ?: "N/A"

val Player.formattedGroupName: String
    get() = groupName.lowercase().replaceFirstChar { it.uppercase() }

val Player.prefix: String
    get() {
        val group = luckPerms?.groupManager?.getGroup(groupName)
        return group?.cachedData?.metaData?.prefix ?: group?.getNodes(NodeType.PREFIX)?.maxByOrNull { it.priority }?.metaValue ?: ""
    }

val Player.color: String
    get() {
        val group = luckPerms?.groupManager?.getGroup(groupName)
        return group?.cachedData?.metaData?.getMetaValue("color") ?: group?.getNodes(NodeType.META)?.firstOrNull { it.metaKey.equals("color", ignoreCase = true) }?.metaValue ?: ""
    }

val Player.formattedRank: String
    get() = prefix + "<color:${player?.color ?: "#FFFFFF"}>" + (player?.formattedGroupName ?: "<gray>no rank")