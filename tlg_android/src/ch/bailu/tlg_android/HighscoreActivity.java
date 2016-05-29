package ch.bailu.tlg_android;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ScrollView;
import android.widget.TextView;
import ch.bailu.tlg.HighscoreList;
import ch.bailu.tlg.PlatformContext;

public class HighscoreActivity extends Activity {
	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView scroll=new ScrollView(this);

		text = new TextView(this);
		text.setTextSize(15f);
		text.setLinkTextColor(new NoDrawContext(this).colorFrame());
		text.setText(Html.fromHtml( getHighscoreText()) );

		scroll.addView(text);
		setContentView(scroll);
	}

	private String getHighscoreText() {
		PlatformContext gc = new NoDrawContext(this);
		HighscoreList list = new HighscoreList(gc);
		return list.getFormatedHTMLText();
	}

}

