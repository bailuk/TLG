package view

import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Grid
import ch.bailu.gtk.type.Str
import ch.bailu.tlg.InternalContext
import config.Strings
import context.BaseContext

class Buttons(iContext: InternalContext, bContext: BaseContext, update: () -> Unit) {

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
