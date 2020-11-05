package ch.bailu.tlg_android;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.ScrollView;
import android.widget.TextView;

public class AboutActivity extends Activity {
    private TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scroll=new ScrollView(this);

        text = new TextView(this);
        text.setTextSize(15f);
        text.setAutoLinkMask(Linkify.WEB_URLS);
        text.setLinkTextColor(new NoDrawContext(this).colorFrame());
        text.setText(Html.fromHtml( getString(R.string.about)) );

        scroll.addView(text);
        setContentView(scroll);
    }


}
