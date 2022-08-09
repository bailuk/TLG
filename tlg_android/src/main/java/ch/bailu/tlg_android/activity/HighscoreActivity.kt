package ch.bailu.tlg_android.activity

import android.app.Activity
import android.os.Bundle
import android.text.Html
import android.widget.ScrollView
import android.widget.TextView
import ch.bailu.tlg_android.Configuration
import ch.bailu.tlg_android.context.AndroidBaseContext
import context.PlatformContext
import score.HighscoreList

class HighscoreActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = ScrollView(this)
        val text = TextView(this)
        text.textSize = 15f
        text.setLinkTextColor(Configuration.frameColor)
        text.text = Html.fromHtml(getHighscoreText())
        scroll.addView(text)
        setContentView(scroll)
    }

    private fun getHighscoreText(): String {
        val bContext: PlatformContext = AndroidBaseContext(this)
        val list = HighscoreList(bContext)
        return list.formatedHTMLText
    }
}
