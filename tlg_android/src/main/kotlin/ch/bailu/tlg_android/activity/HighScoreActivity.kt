package ch.bailu.tlg_android.activity

import android.app.Activity
import android.os.Bundle
import android.text.Html
import android.widget.ScrollView
import android.widget.TextView
import ch.bailu.tlg_android.Configuration
import ch.bailu.tlg_android.context.AndroidBaseContext
import tlg.context.PlatformContext
import tlg.score.HighScoreList

class HighScoreActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = ScrollView(this)
        val text = TextView(this)
        text.textSize = 15f
        text.setLinkTextColor(Configuration.frameColor)
        text.text = Html.fromHtml(getHighScoreText())
        scroll.addView(text)
        setContentView(scroll)
    }

    private fun getHighScoreText(): String {
        val pContext: PlatformContext = AndroidBaseContext(this)
        val list = HighScoreList(pContext)
        return list.formattedHTMLText
    }
}
