package ch.bailu.tlg_android.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import ch.bailu.tlg_android.Configuration
import ch.bailu.tlg_android.R
import ch.bailu.tlg_android.controller.Controller
import tlg.context.InternalContext


class GameActivity : Activity() {
    private var controller: Controller? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val level = TextView(context)
        val score = TextView(context)
        val status = TextView(context)


        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val statusLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(level)
            addView(score)
            addView(status)
        }

        val controller = Controller(context) {
            level.text = "Level: ${it.level}"
            score.text = "Score: ${it.score}"
            status.text = it.toString()
        }

        val buttonLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(Button(context).apply {
                setOnClickListener {
                    controller.pauseOrResume()
                }
                text = "Pause"
            })

            addView(Button(context).apply {
                setOnClickListener {
                    val popup = PopupMenu(context, it)
                    inflateMenu(R.menu.menu, popup.menu)
                    popup.setOnMenuItemClickListener { item->
                        onOptionsItemSelected(item)
                    }
                    popup.show()
                }
                text = "Menu…"
            })

        }

        val topLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            addView(buttonLayout)
            addView(TextView(context), Configuration.MARGIN,Configuration.MARGIN)
            addView(controller.gamePreview.surface, Configuration.PREVIEW_SIZE,Configuration.PREVIEW_SIZE)
            addView(TextView(context), Configuration.MARGIN,Configuration.MARGIN)
            addView(statusLayout)
        }

        mainLayout.addView(topLayout)
        mainLayout.addView(TextView(context), Configuration.MARGIN,Configuration.MARGIN)
        mainLayout.addView(controller.gameMainView.surface)
        setContentView(mainLayout)
        this.controller = controller
    }

    override fun onResume() {
        super.onResume()
        controller?.onActivityResume()
        controller?.updateStatusText()
    }

    override fun onPause() {
        controller?.onActivityPause()
        super.onPause()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        inflateMenu(R.menu.menu, menu)
        return true
    }

    private fun inflateMenu(id: Int, menu: Menu?) {
        menuInflater.inflate(id, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val tetris = controller
        if (tetris is Controller) {
            return when (item.itemId) {
                R.id.menu_grid -> {
                    tetris.toggleGrid()
                    true
                }
                R.id.menu_pause -> {
                    tetris.pauseOrResume()
                    true
                }
                R.id.menu_start -> {
                    tetris.newGame()
                    true
                }
                R.id.menu_about -> {
                    startActivity(AboutActivity::class.java)
                    true
                }
                R.id.menu_score -> {
                    startActivity(HighScoreActivity::class.java)
                    true
                }
                else -> false
            }
        }
        return false
    }

    private fun startActivity(activityClass: Class<*>) {
        val intent = Intent()
        intent.setClass(this, activityClass)
        intent.action = activityClass.name
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        controller?.onTouchEvent(event)
        return true
    }

}
