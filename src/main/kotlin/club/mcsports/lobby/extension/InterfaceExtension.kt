package club.mcsports.lobby.extension

fun forEachInGridScissored(
    startRow: Int,
    endRow: Int,
    startColumn: Int,
    endColumn: Int,
    function: (row: Int, column: Int) -> Unit
) {
    for (row in startRow..endRow) {
        for (column in startColumn..endColumn) {
            function(row, column)
        }
    }
}


fun forEachInGridScissoredIndexed(
    startRow: Int,
    endRow: Int,
    startColumn: Int,
    endColumn: Int,
    function: (row: Int, column: Int, index: Int) -> Unit
) {
    var localIndex = 0

    for (row in startRow..endRow) {
        for (column in startColumn..endColumn) {
            function(row, column, localIndex)
            localIndex++
        }
    }
}
