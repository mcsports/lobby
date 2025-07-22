package club.mcsports.lobby.extension

import fr.mrmicky.fastboard.adventure.FastBoard
import net.kyori.adventure.text.Component

fun FastBoard.extendBottom(vararg lines: Component) {
    val tempLines = this.lines
    tempLines.addAll(lines)
    this.updateLines(tempLines)
}

fun FastBoard.extendTop(vararg lines: Component) {
    val tempLines = this.lines
    tempLines.addAll(0, lines.toList())
    this.updateLines(tempLines)
}