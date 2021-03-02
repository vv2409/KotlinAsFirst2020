package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

class PolynomTest {

    private fun assertApproxEquals(expected: Polynom, actual: Polynom, eps: Double) {
        assertEquals(expected.degree(), actual.degree())
        for (i in 0..expected.degree()) {
            assertEquals(expected.coeff(i), actual.coeff(i), eps)
        }
    }

    @Test
    @Tag("4")
    fun getValue() {
        val p = Polynom(1.0, 3.0, 2.0)
        val p1 = Polynom(2.0, 4.0, 1.0, 0.0, 0.0)
        assertEquals(42.0, p.getValue(5.0), 1e-10)
        assertEquals(68.0, p1.getValue(2.0), 1e-10)
    }

    @Test
    @Tag("4")
    fun degree() {
        val p = Polynom(1.0, 1.0, 1.0)
        assertEquals(2, p.degree())
        val q = Polynom(0.0)
        assertEquals(0, q.degree())
        val r = Polynom(0.0, 1.0, 2.0)
        assertEquals(1, r.degree())
    }

    @Test
    @Tag("4")
    fun plus() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -1.0, 2.0, 6.0)
        assertApproxEquals(r, p1 + p2, 1e-10)
        assertApproxEquals(r, p2 + p1, 1e-10)
    }

    @Test
    @Tag("4")
    operator fun unaryMinus() {
        val p = Polynom(1.0, -1.0, 2.0)
        val r = Polynom(-1.0, 1.0, -2.0)
        val p1 =Polynom(1.0, 3.0, 2.0)
        val r1 =Polynom(-1.0, -3.0, -2.0)
        assertApproxEquals(r, -p, 1e-11)
        assertApproxEquals(r1, -p1, 1e-11)
    }

    @Test
    @Tag("4")
    fun minus() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -3.0, -4.0, 2.0)
        val p = Polynom(1.0, -2.0, -1.0, 4.0)
        val v = Polynom(1.0, 3.0, 2.0, 0.0)
        val res = Polynom(-5.0, -3.0, 4.0)
        assertApproxEquals(res, p - v, 1e-10)
        /**assertApproxEquals(r, p1 - p2, 1e-10)
        assertApproxEquals(-r, p2 - p1, 1e-10)*/
    }

    @Test
    @Tag("6")
    fun times() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0)
        val p3 = Polynom(1.0, 1.0, -1.0, 1.0, 1.0, 5.0)
        var p4 = Polynom(1.0, 1.0, 0.0, -8.0)
        var r2 = Polynom(1.0, 2.0, 0.0, -8.0, -6.0, 14.0, -3.0, -8.0, -40.0)
        var q = Polynom(1.0, 3.0, 2.0)
        var w = Polynom(1.0, 0.0)
        val res = Polynom(1.0,3.0,2.0, 0.0)
        assertApproxEquals(res, q * w, 1e-10)
        assertApproxEquals(r, p1 * p2, 1e-10)
        assertApproxEquals(r, p2 * p1, 1e-10)
        assertApproxEquals(r2, p4 * p3, 1e-10)

    }

    @Test
    @Tag("8")
    fun div() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -5.0)
        assertApproxEquals(r, p1 / p2, 1e-10)
        assertApproxEquals(Polynom(1.0, 2.0), Polynom(2.0, 4.0) / Polynom(0.0, 2.0), 1e-10)
    }

    @Test
    @Tag("8")
    fun rem() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -5.0)
        val q = Polynom(12.0, 14.0)
        assertApproxEquals(q, p1 % p2, 1e-10)
        assertApproxEquals(p1, p2 * r + q, 1e-10)
    }

    @Test
    @Tag("4")
    fun equals() {
        assertEquals(Polynom(1.0, 2.0, 3.0), Polynom(1.0, 2.0, 3.0))
        assertEquals(Polynom(0.0, 2.0, 3.0), Polynom(2.0, 3.0))
    }

    @Test
    @Tag("6")
    fun hashCodeTest() {
        assertEquals(Polynom(1.0, 2.0, 3.0).hashCode(), Polynom(1.0, 2.0, 3.0).hashCode())
    }
}