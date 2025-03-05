package club.mcsports.lobby.scoreboard

import fr.mrmicky.fastboard.adventure.FastBoard
import java.util.*

object ScoreboardService {
    val scoreboardStorage = mutableMapOf<UUID, FastBoard>()
}