package view

import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Grid
import ch.bailu.gtk.type.Str
import config.Strings
import context.GtkBaseContext
import tlg.context.InternalContext

class Buttons(iContext: InternalContext, bContext: GtkBaseContext, update: () -> Unit) {

    val grid = Grid().apply {
        attach(Button().apply {
            iconName = Str("go-previous-symbolic")
            addCssClass(Strings.button)
            onClicked {
                iContext.moveLeft(bContext)
                update()
            }
        }, 0, 1, 1, 1)

        attach(Button().apply {
            iconName = Str("go-next-symbolic")
            addCssClass(Strings.button)
            onClicked {
                iContext.moveRight(bContext)
                update()
            }
        }, 2, 1, 1, 1)

        attach(Button().apply {
            iconName = Str("go-down-symbolic")
            addCssClass(Strings.button)
            onClicked {
                iContext.moveDown(bContext)
                update()
            }
        }, 1, 1, 1, 1)

        attach(Button().apply {
            iconName = Str("go-up-symbolic")
            addCssClass(Strings.button)
            onClicked {
                iContext.moveTurn(bContext)
                update()
            }
        }, 1, 0, 1, 1)
    }
}
