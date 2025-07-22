package club.mcsports.lobby

import app.simplecloud.controller.api.ControllerApi
import app.simplecloud.droplet.player.api.PlayerApi
import club.mcsports.droplet.queue.api.QueueApi
import club.mcsports.lobby.command.SetupCommand
import club.mcsports.lobby.config.ConfigFactory
import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.listener.*
import club.mcsports.lobby.scoreboard.ScoreboardService
import com.noxcrew.interfaces.InterfacesListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Lobby : JavaPlugin() {

    companion object {
        lateinit var playerApiSingleton: PlayerApi.Coroutine
            private set
    }
    private val controllerApi = ControllerApi.createCoroutineApi()

    private val playerApi = PlayerApi.createCoroutineApi().also { playerApiSingleton = it }
    private val queueApi = QueueApi.createCoroutineApi()
    private val gameSelector = GuiGameSelector(playerApi, controllerApi, queueApi)
    private val config = ConfigFactory.loadOrCreate(dataFolder.toPath())

    private val scoreboardService = ScoreboardService(this)

    override fun onEnable() {
        InterfacesListeners.install(this)

        with(Bukkit.getPluginManager()) {
            registerEvents(PlayerJoinListener(this@Lobby, config, scoreboardService), this@Lobby)
            registerEvents(PlayerQuitListener(scoreboardService), this@Lobby)
            registerEvents(PlayerListener(), this@Lobby)
            registerEvents(WorldDestroyListener(), this@Lobby)
            registerEvents(PlayerInteractListener(gameSelector), this@Lobby)
        }
        registerCommand("setup", SetupCommand(dataFolder.toPath(), config))

        scoreboardService.update()
    }

    override fun onDisable() {
        //Plugin shutdown logic
    }

}