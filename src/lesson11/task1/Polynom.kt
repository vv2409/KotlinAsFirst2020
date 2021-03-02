@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.pow


/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 *
 */


class Polynom(vararg coeffs: Double) {


    constructor(l: List<Double>) : this(*l.toDoubleArray())

    private val list = coeffs.toMutableList()
    private fun remove(listPolynom: MutableList<Double>): MutableList<Double> {
        return if (listPolynom.size == 1 && listPolynom[0] == 0.0) listPolynom else {
            while (listPolynom[0] == 0.0 && listPolynom.size != 0) {
                listPolynom.removeAt(0)
            }
            listPolynom
        }
    }

    val listPolynom = remove(list)
    val size = listPolynom.size


    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int) = listPolynom[size - 1 - i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        val lastIndex = listPolynom.lastIndex
        var res = 0.0
        for ((i, element) in listPolynom.withIndex()) {
            res += element * x.pow(lastIndex - i)
        }
        return res
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int = size - 1


    /**
     * Сложение
     */

    operator fun plus(other: Polynom): Polynom {
        val big = if (this.size >= other.size) this.listPolynom else other.listPolynom
        val small = if (this.size < other.size) this.listPolynom else other.listPolynom
        val difference = big.size - small.size
        val res: MutableList<Double> = mutableListOf()
        for ((i, element) in big.withIndex()) res += element + small.getOrElse(i - difference) { 0.0 }
        return Polynom(res)
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom {
        val res = mutableListOf<Double>()
        var i = 0
        while (i != list.size) {
            res.add(-list[i])
            i++
        }
        return Polynom(res)
    }

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = this + -other

    /**
     * Умножение
     */

    operator fun times(other: Polynom): Polynom {
        var resMap = mutableMapOf<Int, Double>()
        for ((key1, value1) in this.listPolynom.withIndex()) {
            for ((key2, value2) in other.listPolynom.withIndex()) {
                val key = size - key1 + other.size - key2 - 2
                val value = value1 * value2
                resMap[key] = (resMap[key] ?: 0.0) + value
            }
        }
        val res = mutableListOf<Double>()
        for ((key, value) in resMap) {
            res += value
        }

        return Polynom(res)
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     *
     */

    operator fun div(other: Polynom): Polynom {
        when {
            other.size > size -> return Polynom(0.0)
            other.size == size -> return when {
                other.listPolynom[0] > listPolynom[0] -> Polynom(0.0)
                else -> Polynom(listPolynom[0] / other.listPolynom[0])
            }
            else -> {
                var denominator = this
                val res = mutableListOf<Double>()
                while (other.size <= denominator.size) {
                    val quotient = denominator.listPolynom[0] / other.listPolynom[0]
                    res += quotient
                    val multiplier = listOf(quotient) + List<Double>(denominator.size - other.size) {0.0}
                    denominator -= other * Polynom(multiplier)
                    if (denominator.size == 1 && denominator.listPolynom[0] == 0.0) break
                }
                return Polynom(res)
            }
        }
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = this - this.div(other) * other

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        return if (other is Polynom) {
            listPolynom == other.listPolynom
        } else false
    }

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int = listPolynom.hashCode()

    override fun toString(): String = listPolynom.toString()

}
