@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1


/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
fun strComplex(s: String): Complex {
    var re = 0.0
    var im = 0.0
    when {
        s == "-i" -> im = -1.0
        s == "i" -> im = 1.0
        s == "+i" -> im = 1.0
        s.matches(Regex("""^-?\d+(\.\d+)*$""")) ->
            re = s.toDouble()
        s.matches(Regex("""^-?\d+(\.\d+)*i$""")) ->
            im = s.substring(0 until s.lastIndex).toDouble()
        s.matches(Regex("""^-?\d+(\.\d+)*[+-]\d+(\.\d+)*i$""")) -> {
            val j = s.indexOf('+', 1)
            val k = s.indexOf('-', 1)
            val i = if (j > k) j else k
            re = s.substring(0 until i).toDouble()
            im = s.substring(i until s.lastIndex).toDouble()
        }
        s.matches(Regex("""^-?\d+(\.\d+)*[+-]*i$""")) -> {
            val j = s.indexOf('+', 1)
            val k = s.indexOf('-', 1)
            val i = if (j > k) j else k
            re = s.substring(0 until i).toDouble()
            im = if (j > k) 1.0 else -1.0

        }
        else -> throw IllegalStateException()
    }
    return Complex(re, im)
}

class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */

    constructor(s: String) : this(strComplex(s).re, strComplex(s).im)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(re * other.re - im * other.im, re * other.im + im * other.re)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex = Complex(
        (re * other.re + im * other.im) / (other.re * other.re + other.im * other.im),
        (im * other.re - re * other.im) / (other.re * other.re + other.im * other.im),
    )


    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Complex && re == other.re && im == other.im


    /**
     * Преобразование в строку
     */
    override fun toString(): String = buildString {
        if (im == 0.0) append("$re") else if (re != 0.0) {
            if (im > 0) append("$re+${im}i") else append("$re${im}i")
        } else append("${im}i")
    }

}
