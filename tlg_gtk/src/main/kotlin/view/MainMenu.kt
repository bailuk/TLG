package view

import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.MenuButton
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import config.Strings
import context.GtkBaseContext
import lib.menu.Actions
import lib.menu.MenuModelBuilder
import tlg.context.InternalContext

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
                HelpDialog(window)
            }
            label(Strings.highScore) {
                HighScoreDialog(window, pContext)
            }
            label(Strings.info) {
                About.show(window)
            }
        }.create(actions)
    }
}
