package club.mcsports.lobby.gui

import club.mcsports.lobby.extension.loreBuilder
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import club.mcsports.lobby.item.ItemComponents
import club.mcsports.lobby.item.PartyItemComponents
import club.mcsports.lobby.listener.PlayerJoinListener
import club.mcsports.lobby.util.LorePredicate
import club.mcsports.lobby.util.Party
import com.noxcrew.interfaces.InterfacesConstants
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildCombinedInterface
import com.noxcrew.interfaces.properties.interfaceProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiManageParty(party: Party) {

    val gui = buildCombinedInterface {

        allowClickingOwnInventoryIfClickingEmptySlotsIsPrevented = false
        preventClickingEmptySlots = true

        initialTitle = miniMessage("<white>⡝⡝⡝⡝⡝⡝⡝⡝⮉")

        rows = 6

        // Pagination buttons only there to provide the texture via pixel/texture offset
        withTransform { pane, view ->

            pane[1, 7] = StaticElement(drawable(ItemComponents.BIG_ARROW_RIGHT.build()))
            pane[1, 1] = StaticElement(drawable(ItemComponents.BIG_ARROW_LEFT.build()))

            pane[2, 7] = StaticElement(drawable(ItemComponents.MEDIUM_ARROW_RIGHT.build()))
            pane[2, 1] = StaticElement(drawable(ItemComponents.MEDIUM_ARROW_LEFT.build()))

        }

        withTransform { pane, view ->

            pane[0, 8] = StaticElement(drawable(ItemComponents.CLOSE_MENU.build())) {
                CoroutineScope(Dispatchers.IO).launch {
                    view.close(InventoryCloseEvent.Reason.PLAYER)
                }
            }

            pane[2, 5] = StaticElement(drawable(ItemComponents.TRASH_BIN.build().also { item ->
                item.editMeta { meta ->
                    meta.displayName(miniMessage("<white>Delete Party"))
                    meta.lore(listOf(miniMessage("<gray>${"Click to delete".toMiniFont()}")))
                }
            })) {
                InterfacesConstants.SCOPE.launch {
                    view.close(InventoryCloseEvent.Reason.PLAYER)
                }

                PlayerJoinListener.parties.remove(view.player.uniqueId)
                view.player.sendMessage(miniMessage("䶵 <white>You've <color:#dc2626>deleted</color> your party."))
            }
        }

        withTransform(interfaceProperty(party.canInvite)) { pane, view ->
            pane[2, 3] = StaticElement(drawable(PartyItemComponents.PARTY_INVITES.build().also { item ->
                item.editMeta {
                    it.lore(
                        it.lore()?.plus(
                            loreBuilder(
                                LorePredicate(
                                    miniMessage("   <white>Enabled"),
                                    { party.canInvite },
                                    miniMessage("<color:#bee7fa>➡ <white>Enabled")
                                ),

                                LorePredicate(
                                    miniMessage("   <white>Disabled"),
                                    { !party.canInvite },
                                    miniMessage("<color:#bee7fa>➡ <white>Disabled")
                                )
                            )
                        )
                    )
                }

            })) {
                party.canInvite = !party.canInvite
                view.redrawComplete()
            }
        }

        withTransform(interfaceProperty(party.isPrivate)) { pane, view ->
            pane[2, 4] =
                StaticElement(drawable(((if (party.isPrivate) ItemComponents.PADLOCK_LOCKED else ItemComponents.PADLOCK_UNLOCKED).build()).also { item ->
                    item.editMeta {
                        it.displayName(miniMessage("<white>Visibility"))
                        it.lore(
                            listOf(miniMessage("<gray>${"Click to toggle".toMiniFont()}"), Component.empty())?.plus(
                                loreBuilder(
                                    LorePredicate(
                                        miniMessage("   <white>Public"),
                                        { !party.isPrivate },
                                        miniMessage("<color:#bee7fa>➡ <white>Public")
                                    ),

                                    LorePredicate(
                                        miniMessage("   <white>Private"),
                                        { party.isPrivate },
                                        miniMessage("<color:#bee7fa>➡ <white>Private")
                                    )
                                )
                            )
                        )
                    }

                })) {
                    party.isPrivate = !party.isPrivate
                    view.redrawComplete()
                }
        }


    }

}