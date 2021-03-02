package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

internal class ComplexTest {

    private fun assertApproxEquals(expected: Complex, actual: Complex, eps: Double) {
        assertEquals(expected.re, actual.re, eps)
        assertEquals(expected.im, actual.im, eps)
    }

    @Test
    @Tag("2")
    fun plus() {
        assertApproxEquals(Complex("4-2i"), Complex("1+2i") + Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("12-6i"), Complex("5-4i") + Complex("7-2.0i"), 1e-10)
        assertApproxEquals(Complex("1-3i"), Complex("1-i") + Complex("0-2.0i"), 1e-10)
        assertApproxEquals(Complex("12"), Complex("5-i") + Complex("7+i"), 1e-10)

    }

    @Test
    @Tag("2")
    operator fun unaryMinus() {
        assertApproxEquals(Complex(-2.0, 1.0), -Complex(2.0, -1.0), 1e-10)
        assertApproxEquals(Complex(0.0, -1.0), -Complex(0.0, 1.0), 1e-10)
        assertApproxEquals(Complex(5.0, 6.0), -Complex(-5.0, -6.0), 1e-10)


    }

    @Test
    @Tag("2")
    fun minus() {
        assertApproxEquals(Complex("-2-2i"), Complex("5-4i") - Complex("7-2i"), 1e-10)
        assertApproxEquals(Complex("-2+6i"), Complex("1+2i") - Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("-2-2i"), Complex("5-i") - Complex("7+i"), 1e-10)
        assertApproxEquals(Complex("1+5i"), Complex("10+2i") - Complex("9-3i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun times() {
        assertApproxEquals(Complex("27-38i"), Complex("5-4i") * Complex("7-2i"), 1e-10)
        assertApproxEquals(Complex("11+2i"), Complex("1+2i") * Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun div() {
        assertApproxEquals(Complex("2.6+0.8i"), Complex("11-8i") / Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("-0.08+0.56i"), Complex("2+2i") / Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("2")
    fun equals() {
        assertApproxEquals(Complex(1.0, 2.0), Complex("1+2i"), 1e-12)
        assertApproxEquals(Complex(1.0, 0.0), Complex(1.0), 1e-12)
    }
    @Test
    fun hashCodeTest() {
        assertEquals(Complex(1.0, 2.0).hashCode(), Complex("1+2i").hashCode())
    }
}