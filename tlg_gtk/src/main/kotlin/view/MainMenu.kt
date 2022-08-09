package view

import tlg.Configuration.HELP_TEXT
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import config.Strings
import context.GtkBaseContext
import tlg.context.InternalContext
import lib.menu.Actions
import lib.menu.MenuModelBuilder

class MainMenu(window: Window, app: Application, iContext: InternalContext, pContext: GtkBaseContext, update: () -> Unit) {
    private val actions = Actions(app)

    val menuButton = MenuButton().apply {
        iconName = Str("open-menu-symbolic")
        menuModel = MenuModelBuilder().apply {
            label(Strings.newGame) {
                iContext.startNewGame(pContext)
                update()
            }
            label(Strings.grid) {
                iContext.toggleGrid()
                update()
            }
            label(Strings.help) {
                MessageDialog(window,
                    DialogFlags.DESTROY_WITH_PARENT.or(DialogFlags.MODAL),
                    MessageType.INFO,
                    ButtonsType.CLOSE,
                    Str(HELP_TEXT)).apply {
                    onResponse {
                        close()
                        destroy()
                    }
                }.show()
            }
            label(Strings.info) {
                About.show(window)
            }
        }.create(actions)
    }
}
