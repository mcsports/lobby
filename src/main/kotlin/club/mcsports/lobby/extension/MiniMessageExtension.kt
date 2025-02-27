package club.mcsports.lobby.extension

import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage

fun miniMessage(text: String) = MiniMessage.miniMessage().deserialize(text).decoration(TextDecoration.ITALIC, false)