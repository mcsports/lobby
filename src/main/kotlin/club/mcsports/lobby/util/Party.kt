package club.mcsports.lobby.util

import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.item.ItemComponents
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.UUID

class Party(val smallestPage: Int = 2, val biggestPage: Int = smallestPage + 1) : Paginator<PartyMember>() {

    override fun createPagination(smallestPage: Int, biggestPage: Int): Map<Int, List<PartyMember>> {
        return super.createPagination(this.smallestPage, this.biggestPage)
    }

    override fun openPage(uuid: UUID, page: Int): List<PartyMember>? {
        if(page < 0) return null
        if(page >= pages.size) return null

        Bukkit.getPlayer(uuid)?.let { player ->
            val next = ItemComponents.ARROW_RIGHT.build()
            next.editMeta { meta ->
                meta.persistentDataContainer.set(
                    NamespacedKey("mcsports", "lobby/action"),
                    PersistentDataType.STRING,
                    "open_next_party_page"
                )
            }

            val prev = ItemComponents.ARROW_LEFT.build()
            prev.editMeta { meta ->
                meta.persistentDataContainer.set(
                    NamespacedKey("mcsports", "lobby/action"),
                    PersistentDataType.STRING,
                    "open_previous_party_page"
                )
            }

            if(page >= pages.size - 1) player.inventory.setItem(8, null)
            else player.inventory.setItem(8, next)

            if(page <= 0) player.inventory.setItem(5, null)
            else player.inventory.setItem(5, prev)

            this[page]?.forEachIndexed { index, partyMember ->

                val wool = ItemStack(Material.entries[this.entries.indexOf(partyMember) + 5])
                wool.editMeta { meta ->
                    meta.displayName(miniMessage("<color:#bee7fa>Player ${partyMember.name}"))
                    meta.lore(listOf(miniMessage("<gray><italic>Role: ${partyMember.role}")))
                }

                val slotShifter = if(page == 0) 5 else 6

                player.inventory.setItem(index + slotShifter, wool)
            }
        }
        return super.openPage(uuid, page)
    }
}