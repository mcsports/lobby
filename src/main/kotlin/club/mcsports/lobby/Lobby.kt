package club.mcsports.lobby

import app.simplecloud.controller.api.ControllerApi
import app.simplecloud.droplet.player.api.PlayerApi
import club.mcsports.droplet.party.api.PartyApi
import club.mcsports.droplet.queue.api.QueueApi
import club.mcsports.lobby.command.SetupCommand
import club.mcsports.lobby.config.ConfigFactory
import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.listener.*
import com.noxcrew.interfaces.InterfacesListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Lobby : JavaPlugin() {

    private val controllerApi = ControllerApi.createCoroutineApi()

    private val playerApi = PlayerApi.createCoroutineApi()
    private val queueApi = QueueApi.createCoroutineApi()
    private val partyApi = PartyApi.createCoroutineApi()
    private val gameSelector = GuiGameSelector(playerApi, controllerApi, queueApi)
    private val config = ConfigFactory.loadOrCreate(dataFolder.toPath())

    override fun onEnable() {
        InterfacesListeners.install(this)

        with(Bukkit.getPluginManager()) {
            registerEvents(PlayerJoinListener(this@Lobby, config, partyApi), this@Lobby)
            registerEvents(PlayerQuitListener(), this@Lobby)
            registerEvents(PlayerListener(), this@Lobby)
            registerEvents(WorldDestroyListener(), this@Lobby)
            registerEvents(PlayerInteractListener(gameSelector, partyApi), this@Lobby)
        }
        registerCommand("setup", SetupCommand(dataFolder.toPath(), config))
    }

    override fun onDisable() {
        //Plugin shutdown logic
    }

}