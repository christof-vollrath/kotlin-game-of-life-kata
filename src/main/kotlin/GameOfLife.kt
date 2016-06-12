import java.util.*

class GameOfLife {
    fun shouldBeAlive(currentState: Boolean, nrOfNeighbors: Int): Boolean =
        when (nrOfNeighbors) {
            0,1 -> false
            2 -> currentState
            3 -> true
            else -> false
        }
    fun shouldBeAlive(cell: Cell): Boolean = shouldBeAlive(isAlive(cell), countLivingNeighbours(cell))

    fun isAlive(x: Int, y: Int): Boolean = isAlive(Cell(x, y))
    fun isAlive(cell: Cell): Boolean = cells.contains(cell)

    fun setAlive(x: Int, y: Int) {
        cells.add(Cell(x, y))
    }

    fun nextIteration() {
        val cellsToCheck = cells + cells.flatMap { neighbours(it) } // Include neighbours to check for newborn
        cells = cellsToCheck.filter { shouldBeAlive(it) }.toHashSet()
    }

    fun countLivingNeighbours(cell: Cell): Int =
            neighbours(cell).filter { cells.contains(it) }.size

    fun neighbours(cell: Cell): Set<Cell> {
        val result = HashSet<Cell>()
        for (dx in -1..1)
            for (dy in -1..1)
                if (! (dx == 0 && dy == 0))
                    // Cell itself doesn't count as neighbour
                    result.add(Cell(cell.x + dx, cell.y + dy))
        return result
    }

    var cells = HashSet<Cell>()
    data class Cell(val x: Int, val y: Int)
}