package club.mcsports.lobby.gui.menu

import club.mcsports.droplet.friends.api.FriendsApi
import club.mcsports.droplet.party.api.PartyApi
import club.mcsports.lobby.extension.format.miniMessage
import club.mcsports.lobby.extension.format.toMiniFont
import club.mcsports.lobby.item.LobbyItem
import club.mcsports.lobby.item.ProfileTabItem
import com.noxcrew.interfaces.InterfacesConstants
import com.noxcrew.interfaces.click.ClickHandler
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.CombinedInterfaceBuilder
import com.noxcrew.interfaces.interfaces.buildCombinedInterface
import com.noxcrew.interfaces.view.InterfaceView
import io.grpc.StatusException
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class GuiProfile(
    private val partyApi: PartyApi.Future,
    private val friendsApi: FriendsApi.Future
) {

    private val selectionFrameDrawable =
        StaticElement(drawable(LobbyItem.SELECTION_FRAME.build()))

    private val tabs = mutableMapOf<UUID, Tab>()

    fun open(player: Player, tab: Tab = tabs[player.uniqueId] ?: Tab.FRIENDS) {
        InterfacesConstants.SCOPE.launch {
            buildCombinedInterface {
                titleSupplier = { Component.empty() }
                rows = 6

                setupNavbar(tab)
            }.open(player)
        }

        tabs[player.uniqueId] = tab
    }

    private fun CombinedInterfaceBuilder.setupNavbar(tab: Tab) {
        val row = tab.row
        val column = tab.column

        withTransform { view, pane ->
            Tab.entries.forEach { entry ->
                view[entry.row - 1, entry.column] = entry.asElement(partyApi, friendsApi, pane) {
                    if (entry != tab) open(pane.player, entry)
                }
            }

            view[row, column] = selectionFrameDrawable
        }
    }

    enum class Tab(val row: Int, val column: Int) {
        FRIENDS(8, 3),
        PARTY(8, 4),
        SETTINGS(8, 5);

        suspend fun asElement(partyApi: PartyApi.Future, friendsApi: FriendsApi.Future, view: InterfaceView, clickHandler: ClickHandler): StaticElement {
            var itemStack: ItemStack

            when (this) {
                FRIENDS -> {
                    val requests = try {
                        friendsApi.getData().getRequests(view.player.uniqueId, 0, 0).await().totalRequests
                    } catch(_: StatusException) { 0 }

                    itemStack = ProfileTabItem.FRIENDS.build()
                    itemStack.editMeta { meta ->
                        meta.lore(
                            meta.lore()?.map { lore ->
                                lore.replaceText { config ->
                                    config.matchLiteral("<friend_requests>").replacement(miniMessage("$requests Requests".toMiniFont()))
                                }
                            }
                        )
                    }
                }

                PARTY -> {
                    val party = try {
                        partyApi.getData().getParty(view.player.uniqueId).await()
                    } catch(_: StatusException) { null }

                    itemStack = ProfileTabItem.PARTY.build()
                    itemStack.editMeta { meta ->
                        meta.lore(
                            meta.lore()?.map { lore ->
                                lore.replaceText { config ->
                                    config.matchLiteral("<party_state>").replacement(miniMessage("<gray>" + (if(party != null) "Click to manage" else "Click to create").toMiniFont()))
                                }
                            }
                        )
                    }
                }

                SETTINGS -> itemStack = ProfileTabItem.SETTINGS.build()
            }

            return StaticElement(drawable(itemStack), clickHandler)
        }
    }
}