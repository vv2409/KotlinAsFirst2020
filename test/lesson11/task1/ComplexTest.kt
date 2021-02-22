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
        assertApproxEquals(strComplex("4-2i"), strComplex("1+2i") + strComplex("3-4i"), 1e-10)
        assertApproxEquals(strComplex("12-6i"), strComplex("5-4i") + strComplex("7-2.0i"), 1e-10)
        assertApproxEquals(strComplex("-3i"), strComplex("-i") + strComplex("-2.0i"), 1e-10)
        assertApproxEquals(strComplex("12"), strComplex("5-i") + strComplex("7+i"), 1e-10)

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
        assertApproxEquals(strComplex("-2-2i"), strComplex("5-4i") - strComplex("7-2i"), 1e-10)
        assertApproxEquals(strComplex("-2+6i"), strComplex("1+2i") - strComplex("3-4i"), 1e-10)
        assertApproxEquals(strComplex("-2-2i"), strComplex("5-i") - strComplex("7+i"), 1e-10)
        assertApproxEquals(strComplex("1+5i"), strComplex("10+2i") - strComplex("9-3i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun times() {
        assertApproxEquals(strComplex("27-38i"), strComplex("5-4i") * strComplex("7-2i"), 1e-10)
        assertApproxEquals(strComplex("11+2i"), strComplex("1+2i") * strComplex("3-4i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun div() {
        assertApproxEquals(strComplex("2.6+0.8i"), strComplex("11-8i") / strComplex("3-4i"), 1e-10)
        assertApproxEquals(strComplex("-0.08+0.56i"), strComplex("2+2i") / strComplex("3-4i"), 1e-10)
    }

    @Test
    @Tag("2")
    fun equals() {
        assertApproxEquals(Complex(1.0, 2.0), strComplex("1+2i"), 1e-12)
        assertApproxEquals(Complex(1.0, 0.0), Complex(1.0), 1e-12)
    }
}