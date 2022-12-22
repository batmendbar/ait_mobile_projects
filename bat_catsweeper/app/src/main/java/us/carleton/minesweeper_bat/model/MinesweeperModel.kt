package us.carleton.minesweeper_bat.model

object MinesweeperModel {
    const val HIDDEN: Short = 0
    const val VISIBLE: Short = 1
    const val FLAGGED: Short = 2
    private val board = Array(5) { Array(5) {CellModel()} }
    private var hiddenCells: Short = 25

    fun getVisibility(row: Int, col: Int): Short =  board[row][col].getVisibility()
    fun getBomb(row: Int, col: Int): Boolean =  board[row][col].getBomb()
    fun getBombsNearby(row: Int, col: Int): Short = board[row][col].getBombsNearby()
    fun getHiddenCellCount(): Short = hiddenCells

    fun toggleCell(row: Int, col: Int, mode: Short) {
        board[row][col].setVisibility(mode)
        hiddenCells--
    }

    fun resetBoard () {
        resetBombs()
        hideCells()
    }
    private fun resetBombs() {
        clearBombs()
        for (i in 0..4) {
            plantRandomBomb()
        }
    }
    private fun clearBombs() {
        for (i in 0..4) {
            for (j in 0..4) {
                board[i][j].setBomb(false)
                board[i][j].setBombsNearby(0)
            }
        }
    }
    private fun plantRandomBomb() {
        while (true){
            val row = (0..4).random()
            val col = (0..4).random()
            if (tryPlantBombOnLocation(row, col)) {
                break
            }
        }
    }
    private fun tryPlantBombOnLocation(row: Int, col: Int): Boolean {
        if (!board[row][col].getBomb()) {
            board[row][col].setBomb(true)
            updateBombNeighbors(row, col)
            return true
        }
        return false
    }
    private fun updateBombNeighbors(row: Int, col: Int) {
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                if (row + i >= 5 || row + i < 0) continue
                if (col + j >= 5 || col + j < 0) continue
                board[row+i][col+j].incrementBombsNearby()
            }
        }
    }
    private fun hideCells() {
        for (i in 0..4) {
            for (j in 0..4) {
                board[i][j].setVisibility(HIDDEN)
            }
        }
        hiddenCells = 25
    }
}