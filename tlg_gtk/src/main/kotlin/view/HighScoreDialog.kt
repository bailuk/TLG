package view

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.type.Str
import context.GtkBaseContext
import tlg.score.HighScoreList

class HighScoreDialog(window: Window, private val pContext: GtkBaseContext) {
    init {
        val highScores = Str(getHighScoreText())
        MessageDialog(window,
            DialogFlags.DESTROY_WITH_PARENT.or(DialogFlags.MODAL),
            MessageType.INFO,
            ButtonsType.CLOSE,
            highScores
        ).apply {
            onResponse {
                close()
                destroy()
                highScores.destroy()
            }
        }.show()
    }

    private fun getHighScoreText(): String {
        val list = HighScoreList(pContext)
        return list.formattedText
    }
}
