package club.mcsports.lobby.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.FoodLevelChangeEvent

class PlayerListener : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPickup(event: EntityPickupItemEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onFood(event: FoodLevelChangeEvent) {
        event.foodLevel = 20
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity is Player) event.isCancelled = true
    }
}