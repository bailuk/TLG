package tlg

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tlg.geometry.TlgPoint
import tlg.geometry.TlgRectangle

class TestGeometry {

    @Test
    fun testPoint() {
        val p1 = TlgPoint(4,5)
        assertEquals(4, p1.x)
        assertEquals(5, p1.y)

        val p2 = TlgPoint(4)
        assertEquals(4, p2.x)
        assertEquals(0, p2.y)

        val p3 = TlgPoint(p1)
        assertEquals(4, p3.x)
        assertEquals(5, p3.y)
    }

    @Test
    fun testRectangle() {
        val r1 = TlgRectangle()
        assertEquals(0, r1.bL.x)
        assertEquals(0, r1.bR.y)
        assertEquals(0, r1.tL.x)
        assertEquals(0, r1.tR.y)

        val r2 = TlgRectangle(1,2,3,4)
        assertEquals(1, r2.bL.x)
        assertEquals(4, r2.bR.y)
        assertEquals(1, r2.tL.x)
        assertEquals(2, r2.tR.y)
    }
}
