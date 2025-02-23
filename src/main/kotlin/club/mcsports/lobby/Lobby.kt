package club.mcsports.lobby

import club.mcsports.lobby.listener.PlayerJoinListener
import com.noxcrew.interfaces.InterfacesListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Lobby : JavaPlugin() {

    override fun onEnable() {
        InterfacesListeners.install(this)

        with(Bukkit.getPluginManager()) {
            registerEvents(PlayerJoinListener(this@Lobby), this@Lobby)
        }
    }

    override fun onDisable() {
        //Plugin shutdown logic
    }

}