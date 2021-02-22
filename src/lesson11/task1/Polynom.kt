@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import lesson4.task1.polynom
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

fun toMap(polynom: Polynom): Map<Int, Double> {
    var i = 0
    var map = mutableMapOf<Int, Double>()
    while (i < polynom.size()) {
        map[polynom.size() - 1 - i] = polynom.list[i]
        i++
    }
    return map
}

class Polynom(vararg coeffs: Double) {


    constructor(l: List<Double>) : this(*l.toDoubleArray())

    val list = coeffs.toList()


    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = list[list.lastIndex - i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var k = 0
        val a = list.lastIndex
        var res = 0.0
        while (k <= a) {
            res += list[k] * x.pow(a - k)
            k++
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
    fun degree(): Int {
        var i = list.size
        var j = 0
        var k = list[j]
        while (k == 0.0 && j != list.size) {
            j++
            if (j != list.size) k = list[j] else break
        }
        val res = list.size - (j + 1)
        return if (res >= 0) res else 0
    }

    fun size(): Int = list.size

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val d: Boolean = this.size() > other.size()
        var i = 0
        var j = 0
        if (d) j = other.size() - this.size() else i = this.size() - other.size()
        val res: MutableList<Double> = mutableListOf()
        while (j != other.size()) {
            res += when {
                i >= 0 && j >= 0 -> (this.list[i] + other.list[j])
                i < 0 && j >= 0 -> other.list[j]
                else -> this.list[i]
            }
            i++
            j++
        }
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
    operator fun minus(other: Polynom): Polynom {
        val d: Boolean = this.size() > other.size()
        var i = 0
        var j = 0
        if (d) j = other.size() - this.size() else i = this.size() - other.size()
        val res: MutableList<Double> = mutableListOf()
        while (j != other.size()) {
            res += when {
                i >= 0 && j >= 0 -> (this.list[i] - other.list[j])
                i < 0 && j >= 0 -> -other.list[j]
                else -> this.list[i]
            }
            i++
            j++
        }
        return Polynom(res)
    }

    /**
     * Умножение
     */


    operator fun times(other: Polynom): Polynom {
        val map1 = toMap(Polynom(list))
        val map2 = toMap(Polynom(other.list))
        var res = mutableMapOf<Int, Double>()
        for ((key1, value1) in map1) {
            for ((key2, value2) in map2) {
                val key = key1 + key2
                val value = value1 * value2
                val value4 = res[key]
                res[key] = value + (value4 ?: 0.0)
            }
        }
        val r = mutableListOf<Double>()
        for ((key, value) in res) {
            r += value
        }
        return Polynom(r)
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

    fun remove(a: MutableList<Double>): MutableList<Double> {
        if (a.size == 1 && a[0] == 0.0) return a else {
            while (a[0] == 0.0 && a.size != 0) {
                a.removeAt(0)
            }
            return a
        }
    }

    operator fun div(other: Polynom): Polynom {
        var a = remove(list.toMutableList())
        var b = remove(other.list.toMutableList())
        val res = mutableListOf<Double>()
        return if (b.size > a.size) Polynom(listOf(0.0))
        else if (b.size == a.size) {
            if (b[0] > a[0]) Polynom(listOf(0.0))
            else Polynom(listOf(a[0] / b[0]))
        } else {
            while (b.size <= a.size && a.size != 0) {
                val c = a[0] / b[0]
                var i = a.size - b.size - 1
                val l = mutableListOf<Double>(c)
                while (i >= 0) {
                    l += 0.0
                    i--
                }
                res += c
                val s = (Polynom(b) * Polynom(l))
                val k = Polynom(a) - s
                a = remove(k.list.toMutableList())
                if (a[0] == 0.0) break
            }
            return Polynom(res)
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
            var a = remove(list.toMutableList())
            var b = remove(other.list.toMutableList())
            a == b
        } else false
    }

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int {
        var res = 0
        val k = list.size
        return this.getValue(k.toDouble()).toInt()

    }
}
