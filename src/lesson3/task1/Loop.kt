@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumberHelper(n: Int): Int = if (n < 10) 1 else 1 + digitNumber(n / 10)
fun digitNumber(n: Int): Int = digitNumberHelper(abs(n))

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fibHelper(a: Int, b: Int, n: Int): Int = if (n == 0) b else fibHelper(b, a + b, n - 1)
fun fib(n: Int): Int = if (n <= 2) 1 else fibHelper(1, 1, n - 2)

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var v = max(m, n)
    while (v % m != 0 || v % n != 0) v++
    return v
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    for (v in 2..n)
        if (n % v == 0)
            return v
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    for (v in n - 1 downTo 1)
        if (n % v == 0)
            return v
    return 1
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun gcd(m: Int, n: Int): Int {
    val newM = max(m, n)
    val newN = min(m, n)
    if (newN == 0)
        return newM
    return gcd(newM % newN, newN)
}

fun isCoPrime(m: Int, n: Int): Boolean = gcd(m, n) == 1

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val sM = sqrt(m.toDouble()).toInt()
    val sN = sqrt(n.toDouble()).toInt()
    return m == n || sM * sM == m || sN * sN == n || sN - sM > 0
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int = if (x == 1) 0 else 1 + collatzSteps(if (x % 2 == 0) x / 2 else 3 * x + 1)

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    val xMod = x % (2 * PI)
    var poweredX = xMod
    var power = 1
    var fact = 1.0
    var sum = xMod
    var delta = xMod
    var sign = 1
    while (abs(delta) > eps) {
        poweredX *= xMod * xMod
        fact *= (power + 1) * (power + 2)
        power += 2
        sign *= -1
        delta = sign * poweredX / fact
        sum += delta
    }
    return sum
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    val xMod = x % (2 * PI)
    var poweredX = 1.0
    var power = 0
    var fact = 1.0
    var sum = 1.0
    var delta = 1.0
    var sign = 1
    while (abs(delta) > eps) {
        poweredX *= xMod * xMod
        fact *= (power + 1) * (power + 2)
        power += 2
        sign *= -1
        delta = sign * poweredX / fact
        sum += delta
    }
    return sum
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revertHelper(n: Int, reversedPart: Int): Int =
    if (n < 10) reversedPart * 10 + n else revertHelper(n / 10, reversedPart * 10 + n % 10)

fun revert(n: Int): Int = if (n < 10) n else revertHelper(n / 10, n % 10)

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigitsHelper(n: Int, c: Int): Boolean =
    n % 10 != c || if (n < 10) false else hasDifferentDigitsHelper(n / 10, c)

fun hasDifferentDigits(n: Int): Boolean = if (n < 10) false else hasDifferentDigitsHelper(n / 10, n % 10)

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun numLen(n: Int): Int = 1 + if (n < 10) 0 else numLen(n / 10)
fun getFirstDigit(n: Int, len: Int): Int = if (n < 10) if (len == 1) n else 0 else getFirstDigit(n / 10, len - 1)
fun squareSequenceDigit(n: Int): Int {
    var seq = 0
    var seqLen = 0
    var baseNum = 1
    var nLeft = n - 1
    var curDigit = 1
    while (nLeft != 0) {
        if (seqLen == 0) {
            baseNum++
            seq = baseNum * baseNum
            seqLen = numLen(seq)
        }
        curDigit = getFirstDigit(seq, seqLen)
        seqLen--
        seq %= 10.0.pow(seqLen).toInt()
        nLeft--
    }
    return curDigit
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var seq = 0
    var seqLen = 0
    var baseNum = 1
    var nLeft = n - 1
    var curDigit = 1
    while (nLeft != 0) {
        if (seqLen == 0) {
            baseNum++
            seq = fib(baseNum)
            seqLen = numLen(seq)
        }
        curDigit = getFirstDigit(seq, seqLen)
        seqLen--
        seq %= 10.0.pow(seqLen).toInt()
        nLeft--
    }
    return curDigit

}
