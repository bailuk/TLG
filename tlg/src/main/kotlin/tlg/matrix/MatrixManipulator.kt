package tlg.matrix

import tlg.geometry.TlgPoint
import java.io.BufferedInputStream

open class MatrixManipulator : MatrixVisible {
    constructor(w: Int, h: Int) : super(w, h) {}
    constructor(m: Matrix) : super(m) {}
    constructor(reader: BufferedInputStream) : super(reader) {}

    fun placeShape(b: MatrixShape): Boolean {
        val r = isShapePlaceable(b)
        if (r) {
            for (x in 0 until b.width) {
                for (y in 0 until b.height) {
                    if (b.get(x, y).isActivated) {
                        getD(b.x + x, b.y + y).set(b.get(x, y))
                    }
                }
            }
        }
        return r
    }

    fun removeShape(b: MatrixShape) {
        for (x in 0 until b.width) {
            for (y in 0 until b.height) {
                if (b.get(x, y).isActivated) {
                    val vx: Int = b.x + x
                    val vy: Int = b.y + y
                    if (isPositionValid(vx, vy)) getD(vx, vy).makeInvisible()
                }
            }
        }
    }

    private fun isPositionFree(x: Int, y: Int): Boolean {
        return isPositionValid(x, y) && get(x, y).isInvisible
    }

    private fun isPositionValid(x: Int, y: Int): Boolean {
        return x < width && y < height && x > -1 && y > -1
    }

    fun moveShape(shape: MatrixShape, pos: TlgPoint): Boolean {
        val r: Boolean
        val oldPos: TlgPoint = shape.pos
        removeShape(shape)
        r = placeShapeAtPos(shape, pos)
        if (!r) {
            placeShapeAtPos(shape, oldPos)
        }
        return r
    }

    private fun placeShapeAtPos(shape: MatrixShape, pos: TlgPoint): Boolean {
        shape.pos = pos
        return placeShape(shape)
    }

    fun moveShape(b: MatrixShape, x: Int, y: Int): Boolean {
        return moveShape(b, TlgPoint(x, y))
    }

    fun isShapePlaceable(shape: MatrixShape): Boolean {
        var r = true
        var x = 0
        while (r && x < shape.width) {
            var y = 0
            while (r && y < shape.height) {
                if (shape.get(x, y).isVisible) {
                    r = isPositionFree(shape.x + x, shape.y + y)
                }
                y++
            }
            x++
        }
        return r
    }

    fun autoPlaceShape(shape: MatrixShape): Boolean {
        var placed = false
        val center = width / 2 - shape.width / 2
        shape.autoOffset()
        var xoffset = 0
        while (!placed && xoffset < width - center) {
            placed = (placeShapeHorizontal(shape, center + xoffset)
                    || placeShapeHorizontal(shape, center - xoffset))
            xoffset++
        }
        return placed
    }

    private fun placeShapeHorizontal(shape: MatrixShape, x: Int): Boolean {
        shape.x = x
        return placeShape(shape)
    }
}
