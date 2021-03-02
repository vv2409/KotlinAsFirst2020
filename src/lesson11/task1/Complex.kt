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
fun Complex(s: String): Complex {
    var re = 0.0
    var im = 0.0

    val match = Regex("""([+|-]?[0-9.]+)?(?![i])([+|-]?[0-9.]*i)?""")
        .find(s) ?: throw IllegalArgumentException("Invalid format")
    re = if (match.groupValues[1] == "") 0.0 else match.groupValues[1].toDouble()
    im = when {
        match.groupValues[2] == "-i" -> -1.0
        match.groupValues[2] == "i" || match.groupValues[2] == "+i" -> 1.0
        match.groupValues[2] == "" -> 0.0
        else -> match.groupValues[2].dropLast(1).toDouble()
    }
    return Complex(re, im)
}

class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)


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


    override fun hashCode(): Int = 19 * im.hashCode() + re.hashCode()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = when {
        im == 0.0 -> "$re"
        im > 0.0 && re != 0.0 -> "$re+${im}i"
        im < 0.0 && re != 0.0 -> "$re${im}i"
        else -> "${im}i"
    }


}
