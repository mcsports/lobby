package club.mcsports.lobby.util

import net.kyori.adventure.text.Component

/**
 * Stores the initial display line, a list of predicates, and the display line to display if one of the predicates match.
 */
data class LorePredicate(val initialDisplayLine: Component, val predicate: () -> Boolean, val matchDisplayLine: Component)