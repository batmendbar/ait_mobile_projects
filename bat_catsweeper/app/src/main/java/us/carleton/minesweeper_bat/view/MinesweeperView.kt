package us.carleton.minesweeper_bat.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import us.carleton.minesweeper_bat.MainActivity
import us.carleton.minesweeper_bat.R
import us.carleton.minesweeper_bat.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?)
    : View(context, attrs) {
    var paintBackGround = Paint()
    var paintLine = Paint()
    var paintText = Paint()
    var isGameActive: Boolean = true
    var bitmapPlaceholder =  BitmapFactory.decodeResource(resources, R.drawable.cat_placeholder)
    var bitmapAngry =  BitmapFactory.decodeResource(resources, R.drawable.angry_cat)
    var bitmapHappy =  BitmapFactory.decodeResource(resources, R.drawable.happy_cat)
    var bitmapEmpty =  BitmapFactory.decodeResource(resources, R.drawable.cat_wrong_flag)

    init {
        setPaintBackGround()
        setPaintLine()
        setPaintText()
        resetGame()
    }
    private fun setPaintBackGround() {
        paintBackGround.color = Color.BLACK
        paintBackGround.style = Paint.Style.FILL
    }
    private fun setPaintLine() {
        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }
    private fun setPaintText() {
        paintText.style = Paint.Style.FILL
        paintText.color = Color.WHITE
        paintText.setTextSize(200f)
    }
    fun resetGame() {
        isGameActive = true
        MinesweeperModel.resetBoard()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBoard(canvas)
        drawCells(canvas)
    }
    private fun drawBoard(canvas: Canvas) {
        drawBackground(canvas)
        drawLines(canvas)
    }
    private fun drawBackground(canvas: Canvas) {
        paintBackGround.color = Color.BLACK
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackGround)
    }
    private fun drawLines(canvas: Canvas) {
        for (i in 1..4) {
            drawHorizontalLine(canvas, (width * i / 5).toFloat())
            drawVerticalLine(canvas, (height * i / 5).toFloat())
        }
    }
    private fun drawHorizontalLine(canvas: Canvas, yCoordinate: Float) {
        canvas.drawLine(0f, yCoordinate, width.toFloat(), yCoordinate, paintLine)
    }
    private fun drawVerticalLine(canvas: Canvas, xCoordinate: Float) {
        canvas.drawLine(xCoordinate, 0f, xCoordinate, height.toFloat(), paintLine)
    }

    private fun drawCells(canvas: Canvas) {
        for (i in 0..4) {
            for (j in 0..4) {
                drawSingleCell(canvas, i, j)
            }
        }
    }
    private fun drawSingleCell(canvas: Canvas, row: Int, col: Int) {
        if (MinesweeperModel.getVisibility(row, col) == MinesweeperModel.VISIBLE) {
            drawTriedCell(canvas, row, col)
        }
        if (MinesweeperModel.getVisibility(row, col) == MinesweeperModel.FLAGGED) {
            drawFlaggedCell(canvas, row, col)
        }
    }
    private fun drawTriedCell(canvas: Canvas, row: Int, col: Int) {
        if (MinesweeperModel.getBomb(row, col)) {
            drawBitMapCell(canvas, bitmapAngry, row, col)
        } else {
            drawColorRect(canvas, row, col, Color.BLUE)
            canvas.drawText(MinesweeperModel.getBombsNearby(row, col).toString(), (row * width / 5).toFloat(), ((col+1) * height / 5).toFloat(), paintText)
        }
    }
    private fun drawFlaggedCell(canvas: Canvas, row: Int, col: Int) {
        if (isGameActive) drawBitMapCell(canvas, bitmapPlaceholder, row, col)
        else {
            if (MinesweeperModel.getBomb(row, col)) {
                drawBitMapCell(canvas, bitmapHappy, row, col)
            } else {
                drawBitMapCell(canvas, bitmapEmpty, row, col)
            }
        }
    }
    private fun drawColorRect(canvas: Canvas, row: Int, col: Int, color: Int) {
        paintBackGround.color = color
        val startX = (row * width / 5).toFloat()
        val startY = (col * height / 5).toFloat()
        val endX = ((row+1) * width / 5).toFloat()
        val endY = ((col+1) * height / 5).toFloat()
        canvas.drawRect(startX, startY, endX, endY, paintBackGround)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isGameActive) return false
        if (event?.action== MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 5)
            val tY = event.y.toInt() / (height / 5)
            toggleCell(tX, tY)
        }
        invalidate()
        return true
    }
    fun toggleCell(row: Int, col: Int) {
        if (MinesweeperModel.getVisibility(row, col) != MinesweeperModel.HIDDEN) return
        if ((context as MainActivity).getMode()) {
            flagCell(row, col)
        } else {
            tryCell(row, col)
        }
        if (isGameActive) checkVictory()
    }
    fun flagCell(row: Int, col: Int) {
        MinesweeperModel.toggleCell(row, col, MinesweeperModel.FLAGGED)
        if (!MinesweeperModel.getBomb(row, col)) {
            (context as MainActivity).setStatusText(resources.getString(R.string.flagged_wrong_cell))
            isGameActive = false
        }
    }
    fun tryCell(row: Int, col: Int) {
        MinesweeperModel.toggleCell(row, col, MinesweeperModel.VISIBLE)
        if (MinesweeperModel.getBomb(row, col)) {
            (context as MainActivity).setStatusText(resources.getString(R.string.tried_wrong_cell))
            isGameActive = false
        }
    }
    private fun checkVictory() {
        if (MinesweeperModel.getHiddenCellCount() == 0.toShort()) {
            isGameActive = false
            (context as MainActivity).setStatusText(resources.getString(R.string.victory))
        }
    }

    private fun drawBitMapCell(canvas: Canvas, bitmap: Bitmap, row: Int, col: Int) {
        canvas?.drawBitmap(bitmap, (width * row) / 5f, (height * col) / 5f, null)
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapAngry = Bitmap.createScaledBitmap(bitmapAngry, width/5, height/5, false)
        bitmapPlaceholder = Bitmap.createScaledBitmap(bitmapPlaceholder, width/5, height/5, false)
        bitmapHappy = Bitmap.createScaledBitmap(bitmapHappy, width/5, height/5, false)
        bitmapEmpty = Bitmap.createScaledBitmap(bitmapEmpty, width/5, height/5, false)
    }
}


