package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.HeaderBar
import ch.bailu.gtk.gtk.Window
import config.Strings
import context.AwtBaseContext
import context.InternalContext

class Header(window: Window, app: Application, iContext: InternalContext, pContext: AwtBaseContext, update: ()->Unit) {
    val headerBar = HeaderBar().apply {
        showTitleButtons = GTK.TRUE

        packStart(Button().apply {
            label = Strings.pause
            onClicked {
                iContext.togglePause(pContext)
                update()
            }
        })
        packEnd(MainMenu(window, app, iContext, pContext, update).menuButton)
    }
}
