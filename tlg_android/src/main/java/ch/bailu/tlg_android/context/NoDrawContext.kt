package ch.bailu.tlg_android.context

import android.content.Context
import ch.bailu.tlg.TlgPoint
import ch.bailu.tlg.TlgRectangle

open class NoDrawContext(c: Context) : AndroidContext(c) {
    override fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint) {}
    override fun drawFilledRectangle(color: Int, rect: TlgRectangle) {}
    override fun drawText(color: Int, rect: TlgRectangle, text: String) {}
    override fun unlockCanvas() {}
    override fun onNewHighscore() {}
}
