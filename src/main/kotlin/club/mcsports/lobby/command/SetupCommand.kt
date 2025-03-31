package club.mcsports.lobby.command

import club.mcsports.lobby.config.Config
import club.mcsports.lobby.config.ConfigFactory
import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.location.SpawnPoint
import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player
import java.nio.file.Path

class SetupCommand(val dataDirectory: Path, val config: Config) : BasicCommand {
    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {
        val sender = commandSourceStack.sender

        if(sender !is Player) {
            sender.sendMessage(miniMessage("<red>This command can only be executed by players."))
            return
        }

        val player = sender

        if(!player.hasPermission("mcsports.lobby.setup")) {
            player.sendMessage(miniMessage("<red>You don't have permission to use this command."))
            return
        }

        if(args.size != 2 || !args[0].equals("spawnPoint", true)) {
            player.sendMessage(miniMessage("<red>Usage: /setup spawnPoint <location>"))
            return
        }

        try {
            val spawnPoint = SpawnPoint.valueOf(args[1])

            config.spawnPoints[spawnPoint] = player.location
            ConfigFactory.save(dataDirectory, config)
            player.sendMessage(miniMessage("<green>Successfully set the spawn point <white>${args[1]}"))
        }catch (e: Exception) {
            player.sendMessage(miniMessage("<red>An error occurred while setting up the location:"))

            if(e is IllegalArgumentException) {
                player.sendMessage(miniMessage("<red>Couldn't find a spawn point with the name <white>${args[1]}"))
            }

            e.printStackTrace()
        }
    }

    override fun suggest(commandSourceStack: CommandSourceStack, args: Array<out String>): MutableCollection<String> {
        if(!commandSourceStack.sender.hasPermission("mcsports.lobby.setup")) return mutableListOf()

        if(args.isEmpty()) return mutableListOf("spawnPoint")
        if(args.size == 2) return SpawnPoint.entries.map { it.name }.toMutableSet()

        return mutableListOf()
    }
}