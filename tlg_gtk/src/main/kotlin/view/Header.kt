package view

import context.BaseContext
import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.HeaderBar
import ch.bailu.gtk.gtk.Window
import ch.bailu.tlg.InternalContext
import config.Strings

class Header(window: Window, app: Application, iContext: InternalContext, bContext: BaseContext, update: ()->Unit) {
    val headerBar = HeaderBar().apply {
        showTitleButtons = GTK.TRUE

        packStart(Button().apply {
            label = Strings.pause
            onClicked {
                iContext.togglePause(bContext)
                update()
            }
        })
        packEnd(MainMenu(window, app, iContext, bContext, ).menuButton)
    }
}
