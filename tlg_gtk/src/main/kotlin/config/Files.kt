package config

import config.Strings.userAgent
import java.io.File

object Files {
    val configDirectory =
        File(System.getProperty("user.home") + "/.config/$userAgent/")

    init {
        configDirectory.mkdirs()
    }

    const val appCss = "app.css"
}
