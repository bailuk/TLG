import ch.bailu.tlg.TlgPoint
import ch.bailu.tlg.TlgRectangle
import java.awt.Graphics

class GtkGraphicsContext(private val graphics: Graphics) : GtkBaseContext() {
    override fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint) {
        graphics.color = super.getAwtColor(color)
        graphics.drawLine(p1.x, p1.y, p2.x, p2.y)
    }

    override fun drawFilledRectangle(color: Int, rect: TlgRectangle) {
        graphics.color = super.getAwtColor(color)
        graphics.fillRect(rect.left, rect.top, rect.width, rect.height)
    }

    override fun drawText(color: Int, rect: TlgRectangle, text: String) {
        graphics.color = super.getAwtColor(color)
        graphics.drawString(text, rect.left, rect.top + rect.height / 2)
    }
}
