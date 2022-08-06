package config

import ch.bailu.gtk.type.Str

object Strings {
    val appID = Str("ch.bailu.tlg")
    val appTitle = Str("TLG")

    val version = Str("v0.1.0")

    val copyright = Str("© 2021, 2022 Lukas Bai, MIT License")
    val website = Str("https://github.com/bailuk/TLG")

    // UX
    val close = Str("Close")
    val pause = Str("Pause")
    const val info = "Info…"
    const val grid = "Toggle grid"
    const val newGame = "New game"

    // CSS
    val linked = Str("linked")

    // Defaults
    val empty = Str("")
}
