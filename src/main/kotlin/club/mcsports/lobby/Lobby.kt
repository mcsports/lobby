package club.mcsports.lobby

import app.simplecloud.controller.api.ControllerApi
import app.simplecloud.droplet.player.api.PlayerApi
import club.mcsports.droplet.queue.api.QueueApi
import club.mcsports.lobby.command.SetupCommand
import club.mcsports.lobby.config.ConfigFactory
import club.mcsports.lobby.gui.GuiGameSelector
import club.mcsports.lobby.gui.player.GuiHotbar
import club.mcsports.lobby.listener.*
import club.mcsports.lobby.util.LobbyScoreboard
import com.noxcrew.interfaces.InterfacesListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Lobby : JavaPlugin() {

    companion object {
        lateinit var playerApiSingleton: PlayerApi.Coroutine
            private set
        lateinit var controllerApiSingleton: ControllerApi.Coroutine
            private set
        lateinit var queueApiSingleton: QueueApi.Coroutine
            private set
    }

    private val controllerApi = ControllerApi.createCoroutineApi().also { controllerApiSingleton = it }
    private val playerApi = PlayerApi.createCoroutineApi().also { playerApiSingleton = it }
    private val queueApi = QueueApi.createCoroutineApi().also { queueApiSingleton = it }
    private val gameSelector = GuiGameSelector(playerApi, controllerApi, queueApi)
    private val config = ConfigFactory.loadOrCreate(dataFolder.toPath())
    private val hotbar = GuiHotbar()

    private val lobbyScoreboard = LobbyScoreboard(this)

    override fun onEnable() {
        InterfacesListeners.install(this)

        with(Bukkit.getPluginManager()) {
            registerEvents(hotbar, this@Lobby)
            registerEvents(PlayerJoinListener(this@Lobby, config, lobbyScoreboard, hotbar), this@Lobby)
            registerEvents(PlayerQuitListener(lobbyScoreboard), this@Lobby)
            registerEvents(PlayerListener(), this@Lobby)
            registerEvents(WorldDestroyListener(), this@Lobby)
            registerEvents(PlayerInteractListener(gameSelector), this@Lobby)
        }
        registerCommand("setup", SetupCommand(dataFolder.toPath(), config))

        lobbyScoreboard.update()
    }

    override fun onDisable() {
        //Plugin shutdown logic
    }

}