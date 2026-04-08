package club.mcsports.lobby.gui.menu

import app.simplecloud.controller.api.ControllerApi
import app.simplecloud.droplet.player.api.PlayerApi
import build.buf.gen.simplecloud.controller.v1.ServerState
import club.mcsports.droplet.queue.api.QueueApi
import club.mcsports.lobby.extension.format.miniMessage
import club.mcsports.lobby.extension.format.toMiniFont
import club.mcsports.lobby.extension.gui.forEachInGridScissoredIndexed
import club.mcsports.lobby.gui.helper.GuiGameModes
import club.mcsports.lobby.item.GameSelectorItem
import club.mcsports.lobby.item.LobbyItem
import com.noxcrew.interfaces.drawable.Drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildCombinedInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiGameSelector(
    private val playerApi: PlayerApi.Future,
    private val controllerApi: ControllerApi.Coroutine,
    private val queueApi: QueueApi.Future,
) {

    val gui = buildCombinedInterface {
        allowClickingOwnInventoryIfClickingEmptySlotsIsPrevented = false
        preventClickingEmptySlots = true
        titleSupplier = { miniMessage("<white>⡝⡝⡝⡝⡝⡝⡝⡝⡝圝") }
        rows = 6

        withTransform { pane, view ->

            pane[0, 8] = StaticElement(Drawable.drawable(LobbyItem.CLOSE_MENU.build())) {
                CoroutineScope(Dispatchers.IO).launch {
                    view.close(InventoryCloseEvent.Reason.PLAYER)
                }
            }

            GuiGameModes.entries.forEach { mode ->
                pane[mode.slotX, mode.slotY] = mode.asElement(view)
            }

            pane[3, 4] = StaticElement(Drawable.drawable(GameSelectorItem.CLUB_HOUSE.build()))
            pane[7, 5] = StaticElement(Drawable.drawable(GameSelectorItem.POOL.build()))
        }

        withTransform { pane, view ->
            val uuid = view.player.uniqueId
            val currentServer = controllerApi.getServers().getServerById(System.getenv("SIMPLECLOUD_UNIQUE_ID"))
            val currentServerGroup = currentServer.group
            val lobbyServerDrawables = controllerApi.getServers().getServersByGroup(currentServerGroup)
                .filter { it.state == ServerState.AVAILABLE }.map { server ->

                    StaticElement(Drawable.drawable((GameSelectorItem.LOBBY_SERVER.build(server.uniqueId == currentServer.uniqueId)).also { itemStack ->
                        itemStack.editMeta { meta ->
                            meta.displayName(meta.displayName()?.replaceText { config ->
                                config.matchLiteral("<service_number>")
                                    .replacement(server.numericalId.toString())
                            })

                            meta.lore(meta.lore()?.map { lore ->
                                lore.replaceText { config ->
                                    config.matchLiteral("<online_player_count>")
                                        .replacement(server.playerCount.toString())
                                }.replaceText { config ->
                                    config.matchLiteral("<click_action>")
                                        .replacement(miniMessage(if(server.uniqueId == currentServer.uniqueId) "<red>${"Already on this server".toMiniFont()}" else "<gray>${"Click to join".toMiniFont()}"))
                                }
                            })
                        }
                    })) {
                        if (server.uniqueId == currentServer.uniqueId) return@StaticElement
                        CoroutineScope(Dispatchers.IO).launch {
                            val serverName = "${server.group}-${server.numericalId}"
                            playerApi.connectPlayer(uuid, serverName)
                        }
                    }
                }


            forEachInGridScissoredIndexed(7, 7, 1, 3) { row, column, index ->
                if (lobbyServerDrawables.size <= index) {
                    return@forEachInGridScissoredIndexed
                }

                pane[row, column] = lobbyServerDrawables[index]
            }

        }
    }
}