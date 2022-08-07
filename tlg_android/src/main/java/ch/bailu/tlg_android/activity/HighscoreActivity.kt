package ch.bailu.tlg_android.activity

import android.app.Activity
import android.os.Bundle
import android.text.Html
import android.widget.ScrollView
import android.widget.TextView
import ch.bailu.tlg.HighscoreList
import ch.bailu.tlg.PlatformContext
import ch.bailu.tlg_android.context.NoDrawContext

class HighscoreActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = ScrollView(this)
        val text = TextView(this)
        text.textSize = 15f
        text.setLinkTextColor(NoDrawContext(this).colorFrame())
        text.text = Html.fromHtml(getHighscoreText())
        scroll.addView(text)
        setContentView(scroll)
    }

    private fun getHighscoreText(): String? {
        val gc: PlatformContext = NoDrawContext(this)
        val list = HighscoreList(gc)
        return list.formatedHTMLText
    }
}
