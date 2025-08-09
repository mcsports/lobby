package club.mcsports.lobby.gui.helper

import club.mcsports.lobby.Lobby
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.extension.toMiniFont
import club.mcsports.lobby.item.GameModeItemComponents
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.view.InterfaceView
import io.grpc.StatusException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

enum class GuiGameModes(
    val slotX: Int,
    val slotY: Int,
    val group: String,
    val queueType: String,
    val component: GameModeItemComponents,
) {
    POWER_GOLF(
        slotX = 2,
        slotY = 6,
        group = "golf",
        queueType = "golf",
        component = GameModeItemComponents.POWER_GOLF,
    ),
    MASTER_CHEFS(
        slotX = 2,
        slotY = 2,
        group = "master-chefs",
        queueType = "master-chefs",
        component = GameModeItemComponents.MASTER_CHEFS,
    ),
    SPRINT(
        slotX = 4,
        slotY = 1,
        group = "sprint",
        queueType = "sprint",
        component = GameModeItemComponents.SPRINT,
    ),
    GLIDE(
        slotX = 5,
        slotY = 3,
        group = "glide",
        queueType = "glide",
        component = GameModeItemComponents.GLIDE,
    ),
    BOWLING(
        slotX = 5,
        slotY = 6,
        group = "bowling",
        queueType = "bowling",
        component = GameModeItemComponents.BOWLING,
    );

    suspend fun asElement(view: InterfaceView): StaticElement {
        val currentPlayers = try {
            val servers = Lobby.controllerApiSingleton.getServers().getServersByGroup(group)

            servers.sumOf { server -> server.playerCount }
        } catch (_: Exception) {
            0
        }

        var canQueue = true
        var text: String? = null

        try {
            val queue = Lobby.queueApiSingleton.getData().getQueueByPlayer(view.player.uniqueId)
            if (queue != null) {
                canQueue = false
                text = "<red>" + "Already enqueued".toMiniFont()
            }
        } catch (_: Exception) {}

        if (canQueue) {
            try {
                if (!Lobby.queueApiSingleton.getData().getAllQueueTypes().any { it.name == this.queueType }) {
                    canQueue = false
                    text = "<red>" + "Currently disabled".toMiniFont()
                }
            } catch (_: Exception) {
            }
        }

        var stack: ItemStack = component.build(false)

        if (!canQueue) {
            val newStack = component.build(true)
            newStack.editMeta { stackMeta ->
                stackMeta.customName(stack.itemMeta.displayName())
            }
            stack = newStack
        }

        stack.editMeta { meta ->
            meta.lore(
                meta.lore()?.map {
                    it.replaceText { config ->
                        config.matchLiteral("<online_player_count>").replacement(currentPlayers.toString())
                    }.replaceText { config ->
                        config.matchLiteral("<click_action>").replacement(miniMessage(text ?: "<gray><italic>${"Click to queue".toMiniFont()}"))
                    }
                })
        }

        return StaticElement(drawable(stack)) staticElement@{
            if (!canQueue) return@staticElement
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Lobby.queueApiSingleton.getInteraction().enqueue(queueType, view.player.uniqueId)
                } catch (_: StatusException) {
                }

                view.close(InventoryCloseEvent.Reason.PLAYER)
            }
        }
    }
}
