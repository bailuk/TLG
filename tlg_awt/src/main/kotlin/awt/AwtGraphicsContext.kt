package awt

import tlg.geometry.TlgPoint
import tlg.geometry.TlgRectangle
import java.awt.Graphics

class AwtGraphicsContext(private val graphics: Graphics) : AwtBaseContext() {
    override fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint) {
        graphics.color = super.getAwtColor(color)
        graphics.drawLine(p1.x, p1.y, p2.x, p2.y)
    }

    override fun drawFilledRectangle(color: Int, rect: TlgRectangle) {
        graphics.color = super.getAwtColor(color)
        graphics.fillRect(rect.left, rect.top, rect.width, rect.height)
    }
}
