@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import ru.spbstu.ktuples.Tuple
import java.lang.IllegalStateException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun monthToDigit(str: String) =
    when (str) {
        "января" -> 1
        "февраля" -> 2
        "марта" -> 3
        "апреля" -> 4
        "мая" -> 5
        "июня" -> 6
        "июля" -> 7
        "августа" -> 8
        "сентября" -> 9
        "октября" -> 10
        "ноября" -> 11
        "декабря" -> 12
        else -> error("Unknown month: $str")
    }

fun daysInMonth(month: Int, year: Int): Int =
    if (month == 2)
        if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0)
            29
        else
            28
    else if (month < 8)
        30 + month % 2
    else
        30 + 1 - month % 2


fun checkDay(day: Int, daysLimit: Int): Int =
    if (day in 1..daysLimit) day else error("Unknown day: $day")

fun dateStrToDigitHelper(strs: List<String>): List<Int> {
    try {
        val md = monthToDigit(strs[1])
        val yd = strs[2].toInt()
        if (yd < 0)
            error("Unknown year $yd")
        val dd = checkDay(strs[0].toInt(), daysInMonth(monthToDigit(strs[1]), yd))
        if (strs.size == 3)
            return listOf(dd, md, yd)
    } catch (e: Exception) {
    }
    return emptyList()
}

fun dateStrToDigit(str: String): String =
    dateStrToDigitHelper(str.split(" ")).joinToString(".") { String.format("%02d", it) }


/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun digitToMonth(digit: Int) =
    when (digit) {
        1 -> "января"
        2 -> "февраля"
        3 -> "марта"
        4 -> "апреля"
        5 -> "мая"
        6 -> "июня"
        7 -> "июля"
        8 -> "августа"
        9 -> "сентября"
        10 -> "октября"
        11 -> "ноября"
        12 -> "декабря"
        else -> error("Unknown month: $digit")
    }

fun dateDigitToStrHelper(strs: List<Int>): List<String> {
    try {
        if (strs.size == 3) {
            val md = digitToMonth(strs[1])
            val yd = String.format("%02d", strs[2])
            if (strs[2] < 0)
                error("Unknown year ${strs[2]}")
            val dd = checkDay(strs[0], daysInMonth(strs[1], strs[2])).toString()
            return listOf(dd, md, yd)
        }
    } catch (e: Exception) {
    }
    return emptyList()
}

fun dateDigitToStr(digital: String): String =
    try {
        dateDigitToStrHelper(digital.split(".").map { it.toInt() }).joinToString(" ") { it }
    } catch (e: Exception) {
        ""
    }

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun checkPhoneNumberFormat(phone: String): String =
    if (phone.matches(Regex("(([+]?\\d)?\\d{3})?\\d+"))) phone else ""

fun flattenPhoneNumber(phone: String): String =
    checkPhoneNumberFormat(phone.replace(Regex("[() -]+"), ""))

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int =
    if (jumps.matches(Regex("((\\d+|[-%]) )+(\\d+|[-%])")))
        jumps.split(" ").filter { it.matches(Regex("\\d+")) }.map { it.toInt() }.max() ?: -1
    else
        -1

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int =
    if (jumps.matches(Regex("((\\d+ [-+%]+) )*(\\d+ [-+%]+)")))
        Regex("(\\d+ [-%]*[+]+[-%]*)").findAll(jumps).map { it.value.split(" ").first() }.map { it.toInt() }.max() ?: -1
    else
        -1

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun operatorToFunc(op: String) =
    when (op.trim()) {
        "+" -> fun(a: Int, b: Int) = a + b
        "-" -> fun(a: Int, b: Int) = a - b
        else -> throw IllegalArgumentException(op)
    }

fun plusMinus(expression: String): Int =
    if (expression.matches(Regex("(\\d+ [+-] )*\\d+"))) {
        val values = Regex("\\d+").findAll(expression).map { it.value.toInt() }
        val initVal = values.first()
        if (values.count() == 0)
            initVal
        else
            Regex("([-+] | [-+])").findAll(expression).map { it.value }.zip(values.drop(1))
                .fold(initVal, { acc, (op, arg) -> operatorToFunc(op)(acc, arg) })
    } else
        throw IllegalArgumentException(expression)

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndexHelper(strings: List<String>, index: Int): Int =
    if (index in 1 until strings.size && strings.size > 1)
        if (strings[index] == strings[index - 1])
            strings.take(index - 1).sumBy { it.length + 1 }
        else
            firstDuplicateIndexHelper(strings, index + 1)
    else
        -1

fun firstDuplicateIndex(str: String): Int =
    firstDuplicateIndexHelper(str.toLowerCase().split(" "), 1)

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String =
    if (description.matches(Regex("(\\W+ \\d+([.]\\d+)?; )*(\\W+ \\d+([.]\\d+)?)")))
        description.split("; ").map {
            val pair = it.split(" ")
            Tuple(pair[0], pair[1].toDouble())
        }.maxBy { it.v1 }?.v0 ?: ""
    else
        ""

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
val RomanRegex =
    Regex("^([M]{1,3})?(C[MD]|[D]?[C]{0,3})?(X[CL]|[L]?[X]{0,3})?(I[VX]|[V]?[I]{0,3})?$")

fun fromRomanThousandsToInt(str: String): Int =
    if (Regex("^[M]{1,3}").matches(str))
        str.length * 1000
    else
        0

fun fromRomanHundredsToInt(str: String): Int =
    when {
        str == "CM" -> 900
        str == "CD" -> 400
        Regex("[D]?[C]{0,3}").matches(str) -> (if (str.contains('D')) 500 else 0) + str.count { it == 'C' } * 100
        else -> 0
    }

fun fromRomanDozensToInt(str: String): Int =
    when {
        str == "XC" -> 90
        str == "XL" -> 40
        Regex("[L]?[X]{0,3}").matches(str) -> (if (str.contains('L')) 50 else 0) + str.count { it == 'X' } * 10
        else -> 0
    }

fun fromRomanOnceToInt(str: String): Int =
    when {
        str == "IX" -> 9
        str == "IV" -> 4
        Regex("[V]?[I]{0,3}$").matches(str) -> (if (str.contains('V')) 5 else 0) + str.count { it == 'I' }
        else -> 0
    }

fun fromRomanGroupsToNumbers(str: String): Int =
    if (str != "")
        fromRomanThousandsToInt(str) + fromRomanHundredsToInt(str) + fromRomanDozensToInt(str) + fromRomanOnceToInt(str)
    else
        0

fun fromRoman(roman: String): Int {
    if (roman == "")
        return -1
    val groups = RomanRegex.matchEntire(roman.trim())?.groups?.toList()
    return groups?.drop(1)?.sumBy { if (it != null) fromRomanGroupsToNumbers(it.value) else 0 } ?: -1
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun checkBracketsHelper(str: List<Char>, openBrackets: Int): Boolean =
    if (str.isNotEmpty())
        when {
            str[0] == '[' -> checkBracketsHelper(str.slice(1 until str.size), openBrackets + 1)
            str[0] == ']' -> if (openBrackets >= 0) checkBracketsHelper(
                str.slice(1 until str.size),
                openBrackets - 1
            ) else false
            else -> throw IllegalArgumentException("Unknown bracket")
        }
    else
        openBrackets == 0

fun checkBrackets(str: String): Boolean =
    checkBracketsHelper(str.toList(), 0)

fun moveCursorToBracket(
    nestedLevel: Int,
    commands: List<Char>,
    cmdId: Int,
    currentBracket: Char,
    targetBracket: Char,
    step: Int
): Int =
    if (cmdId in commands.indices)
        when (commands[cmdId]) {
            targetBracket ->
                if (nestedLevel == 0)
                    cmdId
                else
                    moveCursorToBracket(nestedLevel - 1, commands, cmdId + step, currentBracket, targetBracket, step)
            currentBracket -> moveCursorToBracket(
                nestedLevel + 1,
                commands,
                cmdId + step,
                currentBracket,
                targetBracket,
                step
            )
            else -> moveCursorToBracket(nestedLevel, commands, cmdId + step, currentBracket, targetBracket, step)
        }
    else
        throw IllegalArgumentException("Can't find bracket")


fun moveCursorToOpenBracket(commands: List<Char>, cmdId: Int): Int =
    moveCursorToBracket(0, commands, cmdId - 1, ']', '[', -1)

fun moveCursorToClosedBracket(commands: List<Char>, cmdId: Int): Int =
    moveCursorToBracket(0, commands, cmdId + 1, '[', ']', 1)


fun computeDeviceCellsHelper(
    cmdId: Int,
    cellId: Int,
    limit: Int,
    cells: MutableList<Int>,
    commands: List<Char>
): List<Int> =
    if (cellId in cells.indices)
        if (limit == 0 || cmdId !in commands.indices)
            cells
        else
            when (commands[cmdId]) {
                ' ' -> computeDeviceCellsHelper(cmdId + 1, cellId, limit - 1, cells, commands)
                '+' -> {
                    cells[cellId] += 1
                    computeDeviceCellsHelper(cmdId + 1, cellId, limit - 1, cells, commands)
                }
                '-' -> {
                    cells[cellId] -= 1
                    computeDeviceCellsHelper(cmdId + 1, cellId, limit - 1, cells, commands)
                }
                '>' -> computeDeviceCellsHelper(cmdId + 1, cellId + 1, limit - 1, cells, commands)
                '<' -> computeDeviceCellsHelper(cmdId + 1, cellId - 1, limit - 1, cells, commands)
                '[' ->
                    if (cells[cellId] == 0) {
                        val movedCmdId = moveCursorToClosedBracket(commands, cmdId)
                        computeDeviceCellsHelper(movedCmdId + 1, cellId, limit - 1, cells, commands)
                    } else computeDeviceCellsHelper(cmdId + 1, cellId, limit - 1, cells, commands)
                ']' ->
                    if (cells[cellId] != 0) {
                        val movedCmdId = moveCursorToOpenBracket(commands, cmdId)
                        computeDeviceCellsHelper(movedCmdId + 1, cellId, limit - 1, cells, commands)
                    } else computeDeviceCellsHelper(cmdId + 1, cellId, limit - 1, cells, commands)
                else -> throw IllegalArgumentException("Bad format")
            }
    else
        throw IllegalStateException()

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> =
    if (Regex("[\\[\\]+->< ]+").matches(commands) && checkBrackets(commands.replace(Regex("[^\\[\\]]"), "")))
        computeDeviceCellsHelper(0, cells / 2, limit, MutableList(cells) { 0 }, commands.toList())
    else
        throw IllegalArgumentException("Bad format")
