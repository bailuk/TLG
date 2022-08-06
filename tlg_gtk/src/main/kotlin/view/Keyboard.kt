package view

import BaseContext
import ch.bailu.gtk.GTK
import ch.bailu.gtk.gdk.GdkConstants
import ch.bailu.gtk.gtk.EventControllerKey
import ch.bailu.tlg.InternalContext

class Keyboard(iContext: InternalContext, bContext: BaseContext, updateView: () -> Unit) {
    val eventControllerKey = EventControllerKey().apply {
        onKeyPressed() { keyVal: Int, _, _ ->
            var update = GTK.TRUE
            val key = Key(keyVal)

            if (key.has(GdkConstants.KEY_N, GdkConstants.KEY_n)) {
                iContext.startNewGame(bContext)

            } else if (key.has(GdkConstants.KEY_Down)) {
                iContext.moveDown(bContext)

            } else if (key.has(GdkConstants.KEY_Left)) {
                iContext.moveLeft(bContext)

            } else if (key.has(GdkConstants.KEY_Right)) {
                iContext.moveRight(bContext)

            } else if (key.has(GdkConstants.KEY_Up)) {
                iContext.moveTurn(bContext)

            } else if (key.has(GdkConstants.KEY_G, GdkConstants.KEY_g)) {
                iContext.toggleGrid()

            } else if (key.has(
                    GdkConstants.KEY_P,
                    GdkConstants.KEY_p,
                    GdkConstants.KEY_space
                )
            ) {
                iContext.togglePause(bContext)
            } else {
                update = GTK.FALSE
            }

            if (GTK.IS(update)) {
                updateView()
            }
            update
        }
    }

    private inner class Key(private val key: Int) {
        fun has(vararg keys: Int): Boolean {
            for (k in keys) {
                if (key == k) {
                    return true
                }
            }
            return false
        }
    }
}
