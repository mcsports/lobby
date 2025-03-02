package club.mcsports.lobby.gui

import club.mcsports.lobby.extension.forEachInGridScissoredIndexed
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import club.mcsports.lobby.item.GameModeItemComponents
import club.mcsports.lobby.item.ItemComponents
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildCombinedInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiGameSelector {

    val gui = buildCombinedInterface {
        allowClickingOwnInventoryIfClickingEmptySlotsIsPrevented = false
        preventClickingEmptySlots = true
        initialTitle = miniMessage("<color:#58cbed>${"Game Selector".toMiniFont()}")
        rows = 6

        withTransform { pane, view ->

            val closeButtonDrawable = StaticElement(drawable(ItemComponents.CLOSE_MENU.build())) {
                CoroutineScope(Dispatchers.IO).launch {
                    view.close(InventoryCloseEvent.Reason.PLAYER)
                }
            }

            val gameModeDrawables = GameModeItemComponents.entries.map {
                StaticElement(drawable(

                    it.build().also { itemStack ->
                        itemStack.editMeta { meta ->
                            meta.lore(meta.lore()?.map { lore ->
                                lore.replaceText { config ->
                                    config.matchLiteral("<online_player_count>").replacement((0..5).random().toString())
                                }
                            })
                        }
                    }

                ))
            }

            forEachInGridScissoredIndexed(3, 4, 2, 6) { row, column, index ->
                if (gameModeDrawables.size < index) {
                    return@forEachInGridScissoredIndexed
                }

                if (gameModeDrawables.size == index) {
                    pane[row, column] = closeButtonDrawable
                    return@forEachInGridScissoredIndexed
                }

                pane[row, column] = gameModeDrawables[index]
            }

            /*
            # As the SimpleCloud API is not fixed yet, the code remains commented out for now.

            val uuid = view.player.uniqueId
            val currentServer = controllerApi.getServers().getCurrentServer()
            val currentServerGroup = currentServer.group
            val lobbyServerDrawables = controllerApi.getServers().getServersByGroup(currentServerGroup).map { server ->

                StaticElement(drawable((if (server.uniqueId == currentServer.uniqueId) ItemComponents.LOBBY_SERVER_UNAVAILABLE.build() else ItemComponents.LOBBY_SERVER.build()).also { itemStack ->
                    itemStack.editMeta { meta ->
                        meta.displayName(meta.displayName()?.replaceText { config ->
                            config.matchLiteral("<service_number>").replacement(server.numericalId.toString().toMiniFont())
                        })

                        meta.lore(meta.lore()?.map { lore ->
                            lore.replaceText { config ->
                                config.matchLiteral("<online_player_count>").replacement(server.playerCount.toString())
                            }
                        })
                    }
                })) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val serverName = "${server.group}-${server.numericalId}"
                        playerApi.connectPlayer(uuid, serverName)
                    }
                }
            } */

            val lobbyServerDrawables = (1..5).map { numericalId ->
                StaticElement(drawable((if (numericalId == 1) ItemComponents.LOBBY_SERVER_UNAVAILABLE.build() else ItemComponents.LOBBY_SERVER.build()).also { itemStack ->
                    itemStack.editMeta { meta ->
                        meta.displayName(meta.displayName()?.replaceText { config ->
                            config.matchLiteral("<service_number>").replacement(numericalId.toString().toMiniFont())
                        })

                        meta.lore(meta.lore()?.map { lore ->
                            lore.replaceText { config ->
                                config.matchLiteral("<online_player_count>").replacement((0..5).random().toString())
                            }
                        })
                    }
                })) {
                    CoroutineScope(Dispatchers.IO).launch {
                        view.player.sendMessage(miniMessage("<red>Feature not implemented yet."))
                        view.close(InventoryCloseEvent.Reason.CANT_USE)
                    }
                }
            }

            forEachInGridScissoredIndexed(7, 7, 2, 6) { row, column, index ->
                if (lobbyServerDrawables.size <= index) {
                    return@forEachInGridScissoredIndexed
                }

                pane[row, column] = lobbyServerDrawables[index]
            }


        }
    }

}