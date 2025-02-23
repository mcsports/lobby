package club.mcsports.lobby

import com.noxcrew.interfaces.InterfacesListeners
import org.bukkit.plugin.java.JavaPlugin

class Lobby : JavaPlugin() {

    override fun onEnable() {
        //Plugin startup logic
        InterfacesListeners.install(this)
    }

    override fun onDisable() {
        //Plugin shutdown logic
    }

}