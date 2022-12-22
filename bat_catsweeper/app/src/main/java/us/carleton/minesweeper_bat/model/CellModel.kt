package us.carleton.minesweeper_bat.model
import androidx.transition.Visibility
import us.carleton.minesweeper_bat.model.MinesweeperModel

class CellModel {
    private var visibility : Short = MinesweeperModel.HIDDEN
    private var isBomb: Boolean = false
    private var bombsNearby: Short = 0

    public fun setVisibility (visibilityValue: Short) {
        visibility = visibilityValue
    }

    public fun getVisibility() = visibility

    public fun setBomb (bombValue: Boolean) {
        isBomb = bombValue
    }
    public fun getBomb() = isBomb

    public fun setBombsNearby (bombs: Short) {
        bombsNearby = bombs
    }
    public fun getBombsNearby() = bombsNearby
    public fun incrementBombsNearby() {
        bombsNearby++;
    }
}