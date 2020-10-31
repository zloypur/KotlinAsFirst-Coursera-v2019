@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import kotlin.math.max

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val invGrades = mutableMapOf<Int, MutableList<String>>()
    for ((name, grade) in grades) {
        if (invGrades[grade] == null)
            invGrades[grade] = mutableListOf()
        invGrades[grade]?.add(name)
    }
    return invGrades
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean = a.all { b[it.key] == it.value }

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit = b.forEach { a.remove(it.key, it.value) }

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.toSet().intersect(b.toSet()).toList()

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val union = mapA.toMutableMap()
    for ((key, value) in mapB) {
        val curVal = union[key]
        union[key] = if (curVal == null || curVal == value) value else "$curVal, $value"
    }
    return union
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> =
    stockPrices.map { it.first }.toSet()
        .map { name -> Pair(name, stockPrices.filter { it.first == name }.map { it.second }.average()) }
        .toMap()

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? =
    stuff.filter { it.value.first == kind }.minBy { it.value.second }?.key

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean = word.isEmpty() || chars.toSet() == word.toSet()

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> =
    list.toSet().map { name -> Pair(name, list.count { name == it }) }.filter { it.second > 1 }.toMap()

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean =
    extractRepeats(words.map { it.toSet().sorted().joinToString() }.toList()).any()

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propogateHandshakesHelper(
    currentFriends: Set<String>,
    friends: Map<String, Set<String>>,
    handshakes: Set<String>
): Set<String> =
    if (currentFriends.isEmpty()) handshakes
    else {
        val uniqueFriends = currentFriends.subtract(handshakes)
        propogateHandshakesHelper(
            uniqueFriends.flatMap { friends[it] ?: emptySet() }.toSet(),
            friends,
            handshakes + uniqueFriends
        )
    }

fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> =
    (friends.keys + friends.flatMap { it.value }).toSet()
        .map { name ->
            Pair(
                name,
                propogateHandshakesHelper(friends[name] ?: emptySet(), friends, emptySet()) - name
            )
        }
        .toMap()

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val indexMap = mutableMapOf<Int, MutableList<Int>>()
    for (i in list.indices) {
        val value = list[i]
        if (indexMap[value] == null)
            indexMap[value] = mutableListOf()
        indexMap[value]!!.add(i)
    }
    indexMap.forEach { it.value.sort() }
    for ((value, indexes) in indexMap)
        if (indexes.size > 1 && value + value == number)
            return Pair(indexes[0], indexes[1])

    val sortedList = indexMap.keys.sorted()
    var upperIndex = sortedList.size - 1
    var lowerIndex = 0
    while (lowerIndex < upperIndex) {
        val lower = sortedList[lowerIndex]
        val upper = sortedList[upperIndex]
        val sum = lower + upper
        when {
            sum == number -> {
                val v1 = indexMap[lower]!![0]
                val v2 = indexMap[upper]!![0]
                return if (v1 < v2) Pair(v1, v2) else Pair(v2, v1)
            }
            lowerIndex == upperIndex - 1 -> {
                return if (sum != number) Pair(-1, -1)
                else {
                    val v1 = indexMap[lower]!![0]
                    val v2 = indexMap[upper]!![0]
                    if (v1 < v2) Pair(v1, v2) else Pair(v2, v1)
                }
            }
            sum > number -> {
                upperIndex--
                lowerIndex = 0
            }
            sum < number -> {
                lowerIndex++
                upperIndex = sortedList.size - 1
            }
        }
    }
    return Pair(-1, -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun reconstruct(
    index: Int,
    weight: Int,
    params: List<Pair<String, Pair<Int, Int>>>,
    items: List<List<Int>>,
    result: List<Int>
): List<Int> =
    when {
        items[index][weight] == 0 -> result
        items[index][weight] == items[index - 1][weight] -> reconstruct(index - 1, weight, params, items, result)
        else -> reconstruct(index - 1, weight - params[index - 1].second.first, params, items, result + index)
    }

fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val sortedList = treasures.toList().filter { it.second.first <= capacity }.sortedByDescending { it.second.second }
    if (sortedList.isEmpty() || capacity == 0)
        return setOf()
    val items = mutableListOf<MutableList<Int>>()
    for (i in 0..sortedList.size) {
        val ml = mutableListOf<Int>()
        for (c in 0..capacity)
            ml.add(0)
        items.add(ml)
    }
    for (index in sortedList.indices.map { it + 1 }) {
        for (weight in 0..capacity) {
            val weightOfItem = sortedList[index - 1].second.first
            val costOfItem = sortedList[index - 1].second.second
            items[index][weight] = if (weight >= weightOfItem) max(
                items[index - 1][weight],
                items[index - 1][weight - weightOfItem] + costOfItem
            )
            else items[index - 1][weight]
        }

    }
    val resultIds = reconstruct(items.size - 1, capacity, sortedList, items, emptyList())
    return resultIds.map { sortedList[it - 1].first }.toSet()
}

//{
//    val sortedList = treasures.toList().filter { it.second.first <= capacity }.sortedByDescending { it.second.second }
//    val minimalSize = sortedList.minBy { it.second.first }?.second?.first ?: Int.MAX_VALUE
//
//    if (sortedList.isEmpty() || capacity < minimalSize)
//        return emptySet()
//
//    var maximumCost = 0
//    var maximumBag = emptySet<String>()
//
//    for (initialIndex in sortedList.indices) {
//        var currentCapacity = capacity
//        var currentCost = 0
//        val currentBag = mutableSetOf<String>()
//        for (i in initialIndex until sortedList.size) {
//            if (currentCapacity < minimalSize) break
//            val currentItem = sortedList[i]
//            if (currentCapacity >= currentItem.second.first) {
//                currentCapacity -= currentItem.second.first
//                currentCost += currentItem.second.second
//                currentBag.add(currentItem.first)
//            }
//        }
//        if (currentCost > maximumCost) {
//            maximumBag = currentBag
//            maximumCost = currentCost
//        }
//    }
//    return maximumBag
//}
