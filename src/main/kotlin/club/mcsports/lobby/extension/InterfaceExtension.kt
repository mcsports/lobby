package club.mcsports.lobby.extension

fun forEachInGrid(
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
