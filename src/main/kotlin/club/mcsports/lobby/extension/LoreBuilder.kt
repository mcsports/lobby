package club.mcsports.lobby.extension

import club.mcsports.lobby.util.LorePredicate
import net.kyori.adventure.text.Component

//Used for toggleable stuff like party invites or some things like that
fun loreBuilder(predicates: List<LorePredicate>): List<Component> {
    val lore = mutableListOf<Component>()

    predicates.forEach { predicate ->
        if (predicate.predicate()) lore.add(predicate.matchDisplayLine)
        else lore.add(predicate.initialDisplayLine)
    }

    return lore
}

//Used for toggleable stuff like party invites or some things like that
fun loreBuilder(vararg predicates: LorePredicate) = loreBuilder(predicates.toList())