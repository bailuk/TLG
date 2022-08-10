package tlg

object Configuration {
    const val HELP_TEXT = "COMMANDS:\n" +
            "Arrow-keys:\tmove and turn\n" +
            "'P':\t\t\tpause\n" +
            "'G':\t\t\ttoggle grid\n" +
            "'N':\t\t\tnew game\n"

    const val SCORE_FILE = "score"
    const val SCORE_FILE_ENTRIES = 10

    const val STATE_FILE = "state"
    const val STATE_FILE_VERSION = 5

    const val SHAPE_PER_LEVEL = 7

    const val MATRIX_WIDTH = 11
    const val MATRIX_HEIGHT = 19

    const val PREVIEW_SIZE = 5

    const val LEVEL_MAX = 3

    const val COPYRIGHT = "© 2016-2022 Lukas Bai, CC BY 4.0"
    const val LICENSE = "http://creativecommons.org/licenses/by/4.0/"
    const val WEBSITE = "https://github.com/bailuk/TLG"
    const val VERSION = "v0.1.0"

    const val PITCH = "TLG - multi platform Tetris like game"
}
