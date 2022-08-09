package view

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import ch.bailu.tlg.Constants
import ch.bailu.tlg.InternalContext
import config.Strings
import context.AwtBaseContext
import lib.menu.Actions
import lib.menu.MenuModelBuilder

class MainMenu(window: Window, app: Application, iContext: InternalContext, bContext: AwtBaseContext, update: () -> Unit) {
    private val actions = Actions(app)

    val menuButton = MenuButton().apply {
        iconName = Str("open-menu-symbolic")
        menuModel = MenuModelBuilder().apply {
            label(Strings.newGame) {
                iContext.startNewGame(bContext)
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
                    Str(Constants.HELP_TEXT)).apply {
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
