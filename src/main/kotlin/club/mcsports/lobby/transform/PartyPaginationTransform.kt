package club.mcsports.lobby.transform

import club.mcsports.lobby.extension.miniMessage
import club.mcsports.lobby.util.Party
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.Element
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.grid.GridPoint
import com.noxcrew.interfaces.pane.Pane
import com.noxcrew.interfaces.properties.Trigger
import com.noxcrew.interfaces.transform.builtin.PagedTransformation
import com.noxcrew.interfaces.transform.builtin.PaginationButton
import com.noxcrew.interfaces.view.InterfaceView
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.properties.Delegates

class PartyPaginationTransformation<P : Pane>(
    party: Party,
    back: PaginationButton,
    forward: PaginationButton,
    extraTriggers: Array<Trigger> = emptyArray()
) : PagedTransformation<P>(back, forward, extraTriggers) {

    private var pagination = party.createPagination(2)
    private val values by Delegates.observable(createPartyElements(party).toList()) { _, _, _ ->
        pagination = party.createPagination(2)
        boundPage.max = pagination.size - 1
    }

    init {
        boundPage.max = pagination.size - 1
    }

    override suspend fun invoke(pane: P, view: InterfaceView) {
        val positions = generate(page)
        val slots = positions.size

        val offset = page * slots

        positions.forEachIndexed { index, point ->
            val currentIndex = index + offset

            if (currentIndex >= values.size) {
                return@forEachIndexed
            }

            pane[point] = values[currentIndex]
        }

        super.invoke(pane, view)
    }

    private fun generate(page: Int): List<GridPoint> {
        if (!pagination.containsKey(page)) {
            return emptyList()
        }
        val grids = mutableListOf<GridPoint>()
        val index = if (page == 0) 5 else 6
        for (i in 0 until (pagination[page]?.size ?: 0)) {
            grids.add(GridPoint(3, index + i))
        }
        return grids
    }

    override fun applyButton(pane: Pane, button: PaginationButton) {
        val (point, drawable, increments) = button

        pane[point] = StaticElement(drawable) { ctx ->
            increments[ctx.type]?.let { increment -> page += increment }
            button.clickHandler(ctx.player)
        }
    }

    private fun createPartyElements(party: Party): Collection<Element> {
        return party.entries.mapIndexed { index, entry ->
            val wool = ItemStack(Material.entries[index + 5])
            wool.editMeta { meta ->
                meta.displayName(miniMessage("<color:#bee7fa>Player $index"))
                meta.lore(listOf(miniMessage("<gray><italic>UUID: $entry")))
            }
            return@mapIndexed StaticElement(drawable(wool))
        }
    }
}