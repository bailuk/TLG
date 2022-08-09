package lib.color

/**
 * Representation of a color
 */
interface ColorInterface {
    fun red(): Int
    fun green(): Int
    fun blue(): Int
    fun alpha(): Int
    fun toInt(): Int

    companion object {
        const val GRAY = 0xFF7F7F7F
        const val BLACK = 0xFF000000
        const val MAGENTA = 0xFFFF00FF
        const val WHITE = 0xFFFFFFFF
        const val LTGRAY = 0xFFD3D3D3
        const val DKGRAY = 0xFFD3D3D3
    }
}
