package matrix

import geometry.TlgPoint
import geometry.TlgRectangle
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

open class MatrixWithShape : MatrixManipulator {
    var movingShape: MatrixShape
        private set

    constructor(w: Int, h: Int) : super(w, h) {
        movingShape = MatrixShape()
    }

    constructor(reader: BufferedInputStream) : super(reader) {
        movingShape = MatrixShape(reader)
    }

    @Throws(IOException::class)
    override fun writeState(out: BufferedOutputStream) {
        super.writeState(out)
        movingShape.writeState(out)
    }

    fun setMovingShape(shape: MatrixShape): Boolean {
        movingShape = shape
        return autoPlaceShape(movingShape)
    }

    fun removeMovingShape() {
        removeShape(movingShape)
    }

    fun setRandomMovingShape(level: Int, color: Int, turn: Int) {
        val shape = MatrixShape()
        shape.initializeFormAndColor(level, color)
        movingShape = shape.getRotatedCopy(turn)
        autoPlaceShape(movingShape)
    }

    fun moveShapeLeft() {
        val pos: TlgPoint = movingShape.pos
        pos.x -= 1
        moveShape(movingShape, pos)
    }

    fun moveShapeRight() {
        val pos: TlgPoint = movingShape.pos
        pos.x += 1
        moveShape(movingShape, pos)
    }

    fun moveShapeDown(): Boolean {
        val pos: TlgPoint = movingShape.pos
        pos.y += 1
        return moveShape(movingShape, pos)
    }

    fun moveShapeTurn() {
        val temp = movingShape.getRotatedCopy(1)
        removeShape(movingShape)
        if (placeShape(temp)) movingShape = temp else placeShape(movingShape)
    }

    fun centerMovingShape() {
        val r: TlgRectangle = movingShape.activeArea
        val x: Int = (width - r.width) / 2 - r.left
        val y: Int = (height - r.height) / 2 - r.top
        if (x != 0 || y != 0) {
            moveShape(movingShape, x, y)
        }
    }
}
