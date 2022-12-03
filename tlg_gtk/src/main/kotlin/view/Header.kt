package view

import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.HeaderBar
import config.Strings
import context.GtkBaseContext
import tlg.context.InternalContext

class Header(iContext: InternalContext, pContext: GtkBaseContext, update: ()->Unit) {
    val headerBar = HeaderBar().apply {
        showTitleButtons = true

        packStart(Button().apply {
            label = Strings.pause
            onClicked {
                iContext.togglePause(pContext)
                update()
            }
        })
        packEnd(MainMenu().menuButton)
    }
}
