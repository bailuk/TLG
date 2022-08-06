package view

import context.BaseContext
import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.MenuButton
import ch.bailu.gtk.type.Str
import ch.bailu.tlg.InternalContext
import config.Strings
import lib.menu.Actions
import lib.menu.MenuModelBuilder

class MainMenu(window: ch.bailu.gtk.gtk.Window, app: Application, iContext: InternalContext, bContext: BaseContext) {
    private val actions = Actions(app)

    val menuButton = MenuButton().apply {
        iconName = Str("open-menu-symbolic")
        menuModel = MenuModelBuilder().apply {
            label(Strings.newGame) {
                iContext.startNewGame(bContext)
            }
            label(Strings.grid) {
                iContext.toggleGrid()
            }
            label(Strings.info) {
                About.show(window)
            }
        }.create(actions)
    }
}
