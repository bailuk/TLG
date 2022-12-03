package view

import ch.bailu.gtk.gio.Menu
import ch.bailu.gtk.gtk.Application
import ch.bailu.gtk.gtk.MenuButton
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.lib.handler.action.ActionHandler
import ch.bailu.gtk.type.Str
import config.Keys
import config.Strings
import context.GtkBaseContext
import tlg.context.InternalContext

class MainMenu {

    val menuButton = MenuButton().apply {
        iconName = Str("open-menu-symbolic")

        menuModel = createMenu()

    }

    companion object {
        private fun createMenu(): Menu {
            val menu = Menu()

            menu.append(Strings.newGame, Keys.NEW_GAME.toAppString())
            menu.append(Strings.grid, Keys.GRID.toAppString())
            menu.append(Strings.help, Keys.HELP.toAppString())
            menu.append(Strings.highScore, Keys.HIGH_SCORE.toAppString())
            menu.append(Strings.info, Keys.INFO.toAppString())
            return menu
        }

        fun createActions(app: Application, window: Window, iContext: InternalContext, pContext: GtkBaseContext, update: () -> Unit) {

            ActionHandler.get(app, Keys.NEW_GAME.toString()).onActivate { ->
                iContext.startNewGame(pContext)
                update()
            }
            ActionHandler.get(app, Keys.GRID.toString()).onActivate { ->
                iContext.toggleGrid()
                update()
            }
            ActionHandler.get(app, Keys.HELP.toString()).onActivate { ->
                HelpDialog(window)
            }

            ActionHandler.get(app, Keys.HIGH_SCORE.toString()).onActivate { ->
                HighScoreDialog(window, pContext)
            }

            ActionHandler.get(app, Keys.INFO.toString()).onActivate { ->
                About.show(window)
            }
        }
    }


}
