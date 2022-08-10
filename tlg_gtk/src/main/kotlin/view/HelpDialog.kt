package view

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.gtk.Window
import config.Strings

class HelpDialog(window: Window) {
    init {
        MessageDialog(window,
            DialogFlags.DESTROY_WITH_PARENT.or(DialogFlags.MODAL),
            MessageType.INFO,
            ButtonsType.CLOSE,
            Strings.helpText
        ).apply {
            onResponse {
                close()
                destroy()
            }
        }.show()
    }
}
