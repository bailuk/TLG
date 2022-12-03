package config

enum class Keys {
    NEW_GAME, GRID, HELP, HIGH_SCORE, INFO;

    fun toAppString(): String {
        return "app.${toString()}"
    }
}
