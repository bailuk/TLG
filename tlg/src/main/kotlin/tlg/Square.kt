package tlg

import tlg.context.ByteInteger
import tlg.context.PlatformContext
import tlg.geometry.TlgRectangle
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

class Square {
    private var mode = BACKGROUND
    private var color = 0
    var rect: TlgRectangle = TlgRectangle(0, 0, 0, 0)

    constructor() {}
    constructor(s: Square) {
        set(s)
    }

    constructor(reader: BufferedInputStream) {
        color = ByteInteger.read(reader)
        mode = reader.read()
    }

    @Throws(IOException::class)
    fun writeState(output: BufferedOutputStream) {
        ByteInteger.wrap(color).writeState(output)
        output.write(mode)
    }

    val isVisible: Boolean
        get() = mode != BACKGROUND
    val isInvisible: Boolean
        get() = !isVisible

    fun set(s: Square) {
        color = s.color
        mode = s.mode
    }

    fun setColor(c: Int) {
        color = c
    }

    val isActivated: Boolean
        get() = isVisible

    fun makeVisible() {
        mode = COLOR
    }

    fun makeInvisible() {
        mode = BACKGROUND
    }

    val isGreyedOut: Boolean
        get() = mode == GREYED

    fun enableGreyedOut() {
        if (isInvisible) mode = GREYED
    }

    fun disableGreyedOut() {
        if (isGreyedOut) makeInvisible()
    }

    private fun drawGrid(gc: PlatformContext) {
        gc.drawFilledRectangle(gc.colorBackground(), rect)
        gc.drawLine(gc.colorGrid(), rect.tL, rect.tR)
        gc.drawLine(gc.colorGrid(), rect.tL, rect.bL)
    }

    private fun draw3D(gc: PlatformContext, c: Int) {
        val fillRect = TlgRectangle(rect)
        fillRect.shrink(1)
        gc.drawFilledRectangle(c, fillRect)
        gc.drawLine(gc.colorDark(), rect.tL, rect.tR)
        gc.drawLine(gc.colorDark(), rect.tR, rect.bR)
        gc.drawLine(gc.colorHighlight(), rect.bR, rect.bL)
        gc.drawLine(gc.colorHighlight(), rect.bL, rect.tL)
    }

    private fun draw2D(gc: PlatformContext, c: Int) {
        gc.drawFilledRectangle(c, rect)
    }

    fun update(gc: PlatformContext, drawGrid: Boolean) {
        when (mode) {
            COLOR -> draw3D(gc, color)
            GREYED -> draw3D(gc, gc.colorGrayed())
            BACKGROUND -> if (drawGrid) drawGrid(gc) else draw2D(gc, gc.colorBackground())
        }
    }

    companion object {
        private const val BACKGROUND = 0
        private const val COLOR = 1
        private const val GREYED = 2
    }
}
