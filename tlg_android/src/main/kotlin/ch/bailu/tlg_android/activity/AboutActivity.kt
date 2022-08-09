package ch.bailu.tlg_android.activity

import android.app.Activity
import android.os.Bundle
import android.text.Html
import android.text.util.Linkify
import android.widget.ScrollView
import android.widget.TextView
import ch.bailu.tlg_android.Configuration
import ch.bailu.tlg_android.R

class AboutActivity : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = ScrollView(this)
        val text = TextView(this)
        text.textSize = 15f
        text.autoLinkMask = Linkify.WEB_URLS
        text.setLinkTextColor(Configuration.frameColor)
        text.text = Html.fromHtml(getString(R.string.about))
        scroll.addView(text)
        setContentView(scroll)
    }
}
