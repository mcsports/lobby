package club.mcsports.lobby.extension

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

fun serializeMiniMessage(message: Component) = MiniMessage.miniMessage().serialize(message)
fun miniMessage(text: String) = MiniMessage.miniMessage().deserialize(text).decoration(TextDecoration.ITALIC, false)
fun miniMessage(text: String, vararg resolver: TagResolver) = MiniMessage.builder()
    .tags(TagResolver.resolver(TagResolver.standard(), *resolver))
    .build()
    .deserialize(text)
    .decoration(TextDecoration.ITALIC, false)