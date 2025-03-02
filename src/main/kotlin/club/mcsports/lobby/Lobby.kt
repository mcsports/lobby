package club.mcsports.lobby

import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.listener.*
import com.noxcrew.interfaces.InterfacesListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Lobby : JavaPlugin() {

    private val gameSelector = GuiGameSelector()

    override fun onEnable() {
        InterfacesListeners.install(this)

        with(Bukkit.getPluginManager()) {
            registerEvents(PlayerJoinListener(this@Lobby), this@Lobby)
            registerEvents(PlayerQuitListener(), this@Lobby)
            registerEvents(PlayerListener(), this@Lobby)
            registerEvents(WorldDestroyListener(), this@Lobby)
            registerEvents(PlayerInteractListener(gameSelector), this@Lobby)
        }
    }

    override fun onDisable() {
        //Plugin shutdown logic
    }

}