package club.mcsports.lobby.gui

import club.mcsports.lobby.extension.forEachInGridScissored
import club.mcsports.lobby.extension.forEachInGridScissoredIndexed
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import club.mcsports.lobby.item.ItemComponents
import club.mcsports.lobby.util.Party
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildCombinedInterface
import com.noxcrew.interfaces.utilities.forEachInGrid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.persistence.PersistentDataType

class GuiInvitePlayers(party: Party) {

    val invites = mutableListOf<String>()
    private val nameRegex = "[a-zA-Z0-9_]{3,16}".toRegex()

    val gui = buildCombinedInterface {

        allowClickingOwnInventoryIfClickingEmptySlotsIsPrevented = false
        preventClickingEmptySlots = true
        initialTitle = miniMessage("<white>Invite Players")

        rows = 5

        withTransform { pane, view ->

            pane[0, 8] = StaticElement(drawable(ItemComponents.CLOSE_MENU.build())) {
                CoroutineScope(Dispatchers.IO).launch {
                    view.close(InventoryCloseEvent.Reason.PLAYER)
                }
            }

            // Question mark
            pane[2, 1] = StaticElement(drawable(ItemComponents.QUESTION_MARK.build().also { item ->
                item.editMeta { meta ->
                    meta.displayName(miniMessage("<white>Click to enter players"))
                }
            })) {
                view.runChatQuery { component ->
                    invites.addAll(
                        LegacyComponentSerializer.legacyAmpersand().serialize(component).split(",", " ", ";", ":")
                            .filter { it.isNotBlank() && it.matches(nameRegex) }.toSet())

                    forEachInGridScissoredIndexed(5, 7, 0, 8) { x, y, index ->
                        if(index >= invites.size) return@forEachInGridScissoredIndexed

                        pane[x, y] = StaticElement(drawable(ItemComponents.PARTY_MEMBER.build().also { item ->
                            item.editMeta { meta ->
                                meta.displayName(meta.displayName()?.replaceText {
                                    it.matchLiteral("<player_name>").replacement(invites.elementAt(index))
                                })

                                meta.lore(listOf(
                                    miniMessage("<gray>${"Click to remove".toMiniFont()}")
                                ))

                                meta.persistentDataContainer.let { container ->
                                    container.set(NamespacedKey("mcsports", "lobby/player"), PersistentDataType.STRING, invites.elementAt(index))
                                    container.set(NamespacedKey("mcsports", "lobby/action"), PersistentDataType.STRING, "remove_member_from_invites")
                                }

                            }
                        })) {
                            invites.removeAt(index)
                            view.redrawComplete()
                        }
                    }

                    true
                }
            }

            // Check mark
            pane[2, 7] = StaticElement(drawable(ItemComponents.CHECK_MARK.build().also { item ->
                item.editMeta { meta ->
                    meta.displayName(miniMessage("<white>Invite Players"))
                    meta.lore(listOf(miniMessage("<gray>${"Click to confirm".toMiniFont()}")))
                }
            })) { _ ->
                invites.forEach { inviteName ->
                    Bukkit.getPlayer(inviteName)?.let { invitePlayer ->
                        invitePlayer.sendMessage(miniMessage("䶵 <white>You have been invited to join <color:#0096E8>${view.player.name}</color>'s party!"))
                        invitePlayer.sendMessage(miniMessage("䶵 <gray><color:#a3e635><click:run_command:'/party accept ${view.player.name}'><hover:show_text:'<gray>Click to <color:#a3e635>accept</color> the invite'>Accept</hover></click></color> | <color:#dc2626><click:run_command:'/party deny ${view.player.name}'><hover:show_text:'<gray>Click to <color:#dc2626>deny</color> the invite'>Deny</hover></click></color>"))
                    }

                }
            }
        }
    }

}