package club.mcsports.lobby.config

import club.mcsports.lobby.config.serializer.LocationSerializer
import org.bukkit.Location
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.kotlin.toNode
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.nio.file.Files
import java.nio.file.Path

object ConfigFactory {

    val loader = YamlConfigurationLoader.builder()
        .nodeStyle(NodeStyle.BLOCK)
        .defaultOptions { options ->
            options.serializers {
                it.registerAnnotatedObjects(objectMapperFactory()).build()
                it.register(Location::class.java, LocationSerializer())
            }
        }

    fun loadOrCreate(dataDirectory: Path): Config {
        val path = dataDirectory.resolve("config.yml")
        loader.path(path)

        val builtLoader = loader.build()

        if (!Files.exists(path)) {
            return create(path, builtLoader)
        }

        val configurationNode = builtLoader.load()
        return configurationNode.get() ?: throw IllegalStateException("Config could not be loaded")
    }


    private fun create(path: Path, loader: YamlConfigurationLoader): Config {
        val config = Config()
        if (!Files.exists(path)) {
            path.parent?.let { Files.createDirectories(it) }
            Files.createFile(path)

            val configurationNode = loader.load()
            config.toNode(configurationNode)
            loader.save(configurationNode)
        }

        return config
    }

    fun save(dataDirectory: Path, config: Config) {
        val path = dataDirectory.resolve("config.yml")
        loader.path(path)

        val builtLoader = loader.build()

        val configurationNode = builtLoader.load()
        config.toNode(configurationNode)
        builtLoader.save(configurationNode)
    }

}