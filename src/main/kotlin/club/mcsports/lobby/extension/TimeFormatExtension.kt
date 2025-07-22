package club.mcsports.lobby.extension

fun Long.formatTime(zeroSecondsDisplay: Boolean): String {
    val days = this / 86400000
    val hours = (this % 86400000) / 3600000
    val minutes = (this % 3600000) / 60000
    val seconds = (this % 60000) / 1000

    val builder = StringBuilder()

    if (days > 0) {
        builder.append(days).append("d ")
    }

    if (hours > 0) {
        builder.append(hours).append("h ")
    }

    if (minutes > 0) {
        builder.append(minutes).append("m ")
    }

    if (seconds > 0 || zeroSecondsDisplay) {
        builder.append(seconds).append("s")
    }

    return builder.toString().trim()
}