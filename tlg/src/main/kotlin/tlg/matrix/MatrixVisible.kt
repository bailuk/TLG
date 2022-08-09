package tlg.matrix

import tlg.Square
import tlg.context.PlatformContext
import tlg.geometry.TlgRectangle
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

open class MatrixVisible : Matrix {
    private val dirtyArea: TlgRectangle = TlgRectangle()
    private var drawGrid = false

    constructor(w: Int, h: Int) : super(w, h) {
        markAllAsDirty()
    }

    constructor(m: Matrix) : super(m) {
        markAllAsDirty()
    }

    constructor(reader: BufferedInputStream) : super(reader) {
        if (reader.read() > 0) drawGrid = true
    }

    @Throws(IOException::class)
    override fun writeState(out: BufferedOutputStream) {
        super.writeState(out)
        if (drawGrid) out.write(1) else out.write(0)
    }

    fun erase() {
        for (i in 0 until count()) {
            get(i).makeInvisible()
        }
        markAllAsDirty()
    }

    protected fun markAllAsDirty() {
        dirtyArea.left = 0
        dirtyArea.top = 0
        dirtyArea.width = width
        dirtyArea.height = height
    }

    protected fun markAllAsClean() {
        dirtyArea.left = width - 1
        dirtyArea.top = height - 1
        dirtyArea.bottom = 0 //setWidth(0);
        dirtyArea.right = 0 //setHeight(0);
    }

    fun getD(x: Int, y: Int): Square {
        markSquareAsDirty(x, y)
        return get(x, y)
    }

    private fun markSquareAsDirty(x: Int, y: Int) {
        if (x < dirtyArea.left) dirtyArea.left = x
        if (y < dirtyArea.top) dirtyArea.top = y
        if (x > dirtyArea.right) dirtyArea.right = x
        if (y > dirtyArea.bottom) dirtyArea.bottom = y
    }

    fun toggleGrid() {
        drawGrid = !drawGrid
        markAllAsDirty()
    }

    // center
    var pixelGeometry: TlgRectangle
        get() = getPixelArea(TlgRectangle(0, 0, width - 1, height - 1))
        set(rect) {
            val xoffset: Int
            val yoffset: Int
            var xpos: Int
            var ypos: Int
            var squareSize: Int = rect.width / width
            if (squareSize > rect.height / height) squareSize = rect.height / height

            // center
            xoffset = rect.left + (rect.width - squareSize * width) / 2
            yoffset = rect.top + (rect.height - squareSize * height) / 2
            xpos = xoffset
            for (x in 0 until width) {
                ypos = yoffset
                for (y in 0 until height) {
                    get(x, y).rect =
                        TlgRectangle(xpos, ypos, xpos + squareSize - 1, ypos + squareSize - 1)
                    ypos += squareSize
                }
                xpos += squareSize
            }
            markAllAsDirty()
        }

    private val dirtyPixelArea: TlgRectangle
        get() = getPixelArea(dirtyArea)

    private fun getPixelArea(rect: TlgRectangle): TlgRectangle {
        val ret = TlgRectangle(0, 0, 0, 0)
        if (rect.width > 0 && rect.height > 0) {
            val tl = get(rect.left, rect.top)
            val br = get(rect.right, rect.bottom)
            ret.left = tl.rect.left
            ret.top = tl.rect.top
            ret.right = br.rect.right
            ret.bottom = br.rect.bottom
        }
        return ret
    }

    private val isDirty: Boolean
        get() = dirtyArea.width > 0 && dirtyArea.height > 0

    fun update(pContext: PlatformContext) {
        if (isDirty) {
            pContext.setDirtyRect(dirtyPixelArea)
            for (x in dirtyArea.left..dirtyArea.right) {
                for (y in dirtyArea.top..dirtyArea.bottom) {
                    get(x, y).update(pContext, drawGrid)
                }
            }
        }
        markAllAsClean()
    }

    fun updateAll(pContext: PlatformContext) {
        val r: TlgRectangle = pixelGeometry
        r.grow(1)
        pContext.drawRectangle(pContext.colorFrame(), r)
        markAllAsDirty()
        update(pContext)
    }
}
