package club.mcsports.lobby

import club.mcsports.lobby.command.SetupCommand
import club.mcsports.lobby.config.ConfigFactory
import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.listener.*
import com.noxcrew.interfaces.InterfacesListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Lobby : JavaPlugin() {

    //private val controllerApi = ControllerApi.createCoroutineApi()

    //private val playerApi = PlayerApi.createCoroutineApi()
    private val gameSelector = GuiGameSelector()
    private val config = ConfigFactory.loadOrCreate(dataFolder.toPath())

    override fun onEnable() {
        /*CoroutineScope(Dispatchers.IO).launch {
            println(controllerApi.getGroups().getAllGroups())
            //println(playerApi.getOnlinePlayers())
        } */
        InterfacesListeners.install(this)

        with(Bukkit.getPluginManager()) {
            registerEvents(PlayerJoinListener(this@Lobby), this@Lobby)
            registerEvents(PlayerQuitListener(), this@Lobby)
            registerEvents(PlayerListener(), this@Lobby)
            registerEvents(WorldDestroyListener(), this@Lobby)
            registerEvents(PlayerInteractListener(gameSelector), this@Lobby)
        }

        registerCommand("setup", SetupCommand(dataFolder.toPath(), config))
    }

    override fun onDisable() {
        //Plugin shutdown logic
    }

}