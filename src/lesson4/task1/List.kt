@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.main
import org.w3c.dom.ranges.RangeException
import ru.spbstu.ktuples.Tuple
import ru.spbstu.ktuples.zip
import java.lang.Exception
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.fold(0.0, { acc: Double, value: Double -> acc + value * value }))

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0 else list.average()

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isNotEmpty()) {
        val avg = list.average()
        list.replaceAll { it - avg }
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int =
    if (a.isEmpty() || b.isEmpty()) 0 else zip(a, b).map { (v0, v1) -> v0 * v1 }.sum()

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var pwdX = 1
    var result = 0
    for (coef in p) {
        result += coef * pwdX
        pwdX *= x
    }
    return result
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    if (list.isNotEmpty())
        for (i in 1 until list.size) {
            list[i] = list[i] + list[i - 1]
        }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var multipliers = emptyList<Int>()
    var multiplier = 2
    var varN = n
    while (varN != 1) {
        if (varN % multiplier == 0) {
            multipliers = multipliers + multiplier
            varN /= multiplier
        } else
            multiplier++
    }
    return multipliers.sorted()
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString("*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convertHelper(n: Int, base: Int, acc: List<Int>): List<Int> =
    if (n == 0) acc.reversed() else convertHelper(n / base, base, acc + n % base)

fun convert(n: Int, base: Int): List<Int> = if (n == 0) listOf(0) else convertHelper(n, base, emptyList())

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String = convert(n, base).joinToString("") {
    when {
        it < 10 -> ('0' + it).toString()
        it in 10..36 -> ('a' + (it - 10)).toString()
        else -> ""
    }
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimalHelper(digits: List<Int>, base: Int, power: Int, acc: Int): Int =
    if (digits.isEmpty()) acc
    else decimalHelper(
        digits.slice(1 until digits.size),
        base,
        power / base,
        digits[0] * power + acc
    )

fun decimal(digits: List<Int>, base: Int): Int =
    decimalHelper(digits, base, base.toDouble().pow(digits.size - 1).toInt(), 0)

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int = decimal(
    str.map {
        when {
            it <= '9' -> it - '0'
            it in 'a'..'z' -> it - 'a' + 10
            else -> throw Exception("")
        }
    },
    base
)

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun toRoman(n: Int, mainLimit: Int, supLimit: Int, mainChar: String, supChar: String): String {
    if (n < 4)
        return mainChar.repeat(n)
    val mainCnt = n / mainLimit
    val supN = (n - mainLimit * mainCnt) / supLimit
    return when {
        supN == 0 -> mainChar.repeat(mainCnt)
        supN == (mainLimit - supLimit) / supLimit -> mainChar.repeat(mainCnt) + supChar + mainChar
        supN in 1..3 -> mainChar.repeat(mainCnt) + supChar.repeat(supN)
        else -> ""
    }
}

fun roman(n: Int): String {
    var N = n
    var result = ""

    val romanNumbers = listOf(
        Tuple(1000, 100, "M", "C"),
        Tuple(500, 100, "D", "C"),
        Tuple(100, 10, "C", "X"),
        Tuple(50, 10, "L", "X"),
        Tuple(10, 1, "X", "I"),
        Tuple(5, 1, "V", "I")
    )

    if (N >= 4)
        for ((mainLimit, supLimit, mainChar, supChar) in romanNumbers)
            if (N >= mainLimit - supLimit) {
                var m = N - N % mainLimit
                val mm = N - m
                m += mm - mm % (mainLimit - supLimit)
                N -= m
                result += toRoman(m, mainLimit, supLimit, mainChar, supChar)
            }

    result += toRoman(N, 1, 1, "I", "")

    return result
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun getWord(n: Int, thousand: Boolean): String =
    when (n) {
        900 -> "девятьсот"
        800 -> "восемьсот"
        700 -> "семьсот"
        600 -> "шестьсот"
        500 -> "пятьсот"
        400 -> "четыреста"
        300 -> "триста"
        200 -> "двести"
        100 -> "сто"
        90 -> "девяносто"
        80 -> "восемьдесят"
        70 -> "семьдесят"
        60 -> "шестьдесят"
        50 -> "пятьдесят"
        40 -> "сорок"
        30 -> "тридцать"
        20 -> "двадцать"
        19 -> "девятнадцать"
        18 -> "восемнадцать"
        17 -> "семнадцать"
        16 -> "шестнадцать"
        15 -> "пятнадцать"
        14 -> "четырнадцать"
        13 -> "тринадцать"
        12 -> "двенадцать"
        11 -> "одиннадцать"
        10 -> "десять"
        9 -> "девять"
        8 -> "восемь"
        7 -> "семь"
        6 -> "шесть"
        5 -> "пять"
        4 -> "четыре"
        3 -> "три"
        2 -> if (thousand) "две" else "два"
        1 -> if (thousand) "одна" else "один"
        else -> ""
    }

fun decomposeToTerms(n: Int, digitOffset: Int): List<Int> =
    if (n < 20) listOf(n)
    else listOf((n / digitOffset) * digitOffset) + decomposeToTerms(n % digitOffset, digitOffset / 10)

fun getThousandStr(n: Int): String =
    when {
        n == 0 -> ""
        n % 10 == 0 || n % 10 in 5..9 || n % 100 in 11..19 -> "тысяч"
        n % 10 == 1 -> "тысяча"
        n % 10 in 2..4 -> "тысячи"
        else -> ""
    }

fun russian(n: Int): String {
    val thousand = n / 1000
    val hundreds = n % 1000
    val thousandStr = decomposeToTerms(thousand, 100).joinToString(" ") { getWord(it, true) }.trim()
    val hundredsStr = decomposeToTerms(hundreds, 100).joinToString(" ") { getWord(it, false) }.trim()
    val thousandPostfix = getThousandStr(thousand)
    var result = ""
    result += if (thousandStr != "") "$thousandStr " else ""
    result += if (thousandPostfix != "") "$thousandPostfix " else ""
    result += if (hundredsStr != "") hundredsStr else ""
    return result.trim()
}