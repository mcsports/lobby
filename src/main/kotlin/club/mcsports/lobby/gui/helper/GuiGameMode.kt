package club.mcsports.lobby.gui.helper

import club.mcsports.lobby.Lobby
import club.mcsports.lobby.extension.miniMessage
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
    val disabledComponent: GameModeItemComponents? = null,
) {
    POWER_GOLF(
        slotX = 2,
        slotY = 6,
        group = "golf",
        queueType = "golf",
        component = GameModeItemComponents.POWER_GOLF,
        disabledComponent = GameModeItemComponents.POWER_GOLF_UNAVAILABLE,
    ),
    MASTER_CHEFS(
        slotX = 2,
        slotY = 2,
        group = "master-chefs",
        queueType = "master-chefs",
        component = GameModeItemComponents.MASTER_CHEFS,
        disabledComponent = GameModeItemComponents.MASTER_CHEFS_UNAVAILABLE,
    ),
    SPRINT(
        slotX = 4,
        slotY = 1,
        group = "sprint",
        queueType = "sprint",
        component = GameModeItemComponents.SPRINT,
        disabledComponent = GameModeItemComponents.SPRINT_UNAVAILABLE,
    ),
    GLIDE(
        slotX = 5,
        slotY = 3,
        group = "glide",
        queueType = "glide",
        component = GameModeItemComponents.GLIDE,
        disabledComponent = GameModeItemComponents.GLIDE_UNAVAILABLE,
    ),
    BOWLING(
        slotX = 5,
        slotY = 6,
        group = "bowling",
        queueType = "bowling",
        component = GameModeItemComponents.BOWLING,
        disabledComponent = GameModeItemComponents.BOWLING_UNAVAILABLE,
    );

    suspend fun asElement(view: InterfaceView): StaticElement {
        var currentPlayers = 0
        try {
            val servers = Lobby.controllerApiSingleton.getServers().getServersByGroup(group)
            servers.forEach { server -> currentPlayers += server.playerCount.toInt() }
        } catch (_: Exception) {
            currentPlayers = 0
        }

        var canQueue = true
        var text: String? = null
        try {
            val queue = Lobby.queueApiSingleton.getData().getQueueByPlayer(view.player.uniqueId)
            if (queue != null) {
                canQueue = false
                text = "You are already enqueued."
            }
        } catch (_: Exception) {

        }
        if (canQueue) {
            try {
                if (!Lobby.queueApiSingleton.getData().getAllQueueTypes().any { it.name == this.queueType }) {
                    canQueue = false
                    text = "This gamemode is currently disabled."
                }
            } catch (_: Exception) {
            }
        }

        var stack: ItemStack = component.build()

        if (!canQueue) {
            val newStack = disabledComponent?.build() ?: ItemStack(Material.BARRIER)
            newStack.editMeta { stackMeta ->
                stackMeta.customName(stack.itemMeta.displayName())
            }
            stack = newStack
            stack.editMeta { meta ->
                meta.lore(listOf(miniMessage("<color:#dc2626>${text}")))
            }

        } else {
            stack.editMeta { meta ->
                meta.lore(
                    meta.lore()?.map {
                        it.replaceText { config ->
                            config.matchLiteral("<online_player_count>").replacement(currentPlayers.toString())
                        }
                    })
            }
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
