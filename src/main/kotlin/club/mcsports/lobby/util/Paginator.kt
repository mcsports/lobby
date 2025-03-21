package club.mcsports.lobby.util

import java.util.UUID
import kotlin.math.ceil

open class Paginator<T>(list: List<T> = emptyList()) {

    val entries = mutableListOf<T>()
    var pages = mutableMapOf<Int, List<T>>()
    private val view = mutableMapOf<UUID, Int>()

    init {
        entries.addAll(list)
    }

    operator fun get(index: Int): List<T>? = pages[index]

    operator fun set(index: Int, value: List<T>) {
        pages[index] = value
    }

    open fun createPagination(smallestPage: Int = 2, biggestPage: Int = smallestPage + 1): Map<Int, List<T>> {
        val pages = mutableMapOf<Int, List<T>>()

        val pageDifference = biggestPage - smallestPage
        val pagePrecondition = entries.size >= biggestPage * smallestPage
        val pageCalculation =
            if (pagePrecondition) ((entries.size - pageDifference * 2).toDouble() / smallestPage.toDouble()) else ((entries.size - pageDifference).toDouble() / smallestPage.toDouble())
        val pageCount = if (pageCalculation <= 0.0) 1 else ceil(pageCalculation).toInt()

        val tempMembers = entries.toMutableList()

        for (i in 0 until pageCount) {
            val takeElements = if (i == 0 || i == pageCount - 1) biggestPage else smallestPage
            pages[i] = tempMembers.take(takeElements).also { tempMembers.removeAll(it) }
        }

        return pages.also { this.pages = it }
    }

    fun isViewingLast(uuid: UUID): Boolean = view[uuid] == pages.size - 1

    fun isViewingFirst(uuid: UUID): Boolean = view[uuid] == 0

    fun isViewingPage(uuid: UUID, page: Int): Boolean = view[uuid] == page

    open fun openPage(uuid: UUID, page: Int) = pages[page]?.also { view[uuid] = page }

    fun openPreviousPage(uuid: UUID) = openPage(uuid, view[uuid]?.minus(1) ?: 0)

    fun openNextPage(uuid: UUID) = openPage(uuid, view[uuid]?.plus(1) ?: 0)

    open fun close(uuid: UUID) {
        view.remove(uuid)
    }

}