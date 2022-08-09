package ch.bailu.tlg_android.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.widget.EditText
import ch.bailu.tlg_android.context.AndroidBaseContext
import tlg.context.InternalContext

class NameDialog(private val iContext: InternalContext, private val pContext: AndroidBaseContext) : DialogInterface.OnClickListener {
    private val edit: EditText
    override fun onClick(dialog: DialogInterface, which: Int) {
        iContext.setHighScoreName(pContext, edit.text.toString())
    }

    init {
        val title = "Your name?"
        edit = EditText(pContext.androidContext)
        val builder = AlertDialog.Builder(pContext.androidContext)
        val dialog: Dialog
        builder.setTitle(title)
        builder.setView(edit)
        builder.setCancelable(true)
        builder.setPositiveButton(
            "ok",
            this
        )
        builder.setNegativeButton(
            "cancel"
        ) { _, _ -> }
        dialog = builder.create()
        dialog.show()
    }
}
