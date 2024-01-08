package com.example.deskrstatistik.Utility

import kotlin.math.pow
import kotlin.math.round


fun roundToThreeDecimalPlaces(number: Number): String {
    val roundedNumber = round(number.toDouble() * 1000) / 1000
    return "%.3f".format(roundedNumber)
}

// zu 5
fun arithmeticMean(anyList: List<Float>): String {
    if (anyList.isEmpty()) return "0.0"
    val sum = anyList.sum()
    val average = sum / anyList.size

    return roundToThreeDecimalPlaces(average)
}

fun getSum(anyList: List<Float>): String {
    val sum = anyList.sum()

    return roundToThreeDecimalPlaces(sum)
}

// zu 6
fun List<Float>.median(): String {
    if (isEmpty()) return "0.0"

    return if (size % 2 == 1) {
        roundToThreeDecimalPlaces(this[(size - 1) / 2])
    } else {
        roundToThreeDecimalPlaces((this[size / 2 - 1] + this[size / 2]) / 2.0f)
    }
}

// zu 7
fun List<Float>.modes(): String {
    val frequencyMap = groupingBy { it }.eachCount()
    val maxFrequency = frequencyMap.maxOfOrNull { it.value } ?: return "0.0"

    return if (maxFrequency > 1) {
        frequencyMap.filter { it.value == maxFrequency }.keys.toList().toString()
    } else "0.0"
}

// zu 8
//Definition
fun <T : Number> isQuantileCalculable(numbersList: List<T>, p: Double): Boolean {
    val n = numbersList.size

    // Überprüft, ob p im gültigen Bereich liegt
    if (p <= 0.0 || p > 1.0) {
        return false
    }

    // Überprüft die erste Bedingung: p < 1/n
    if (p < (1.0 / n)) {
        return false
    }
    // Wenn keine der Bedingungen zutrifft, ist das Quantil berechenbar
    return true
}

//prüft ob ganzzahlig oder nicht
fun isWholeNumber(value: Double): Boolean {
    return value % 1.0 == 0.0
}

//berücksichtigt ob np ganzzahlig ist und gibt das Element in der Position j zurück
fun <T : Number> calculateQuantile(numbersList: List<T>, p: Double): Double {
    // Sortieren der Liste
    val sortedList = numbersList.map { it.toDouble() }.sorted()

    // Berechnen des Index für das Quantil
    val np = p * sortedList.size
    val j = np.toInt()

    // Überprüfen, ob np ganzzahlig ist
    return if (np % 1.0 != 0.0) {
        // np ist nicht ganzzahlig
        sortedList[j] // Nimmt den Wert bei Index j
    } else {
        // np ist ganzzahlig, und wir müssen sicherstellen, dass j kein Index außerhalb der Liste ist
        if (j == sortedList.size) {
            // Wenn j gleich der Größe der Liste ist, nehmen wir den letzten Wert in der Liste
            sortedList[j - 1]
        } else {
            // Mittelwert von Index j und j+1, wenn j nicht der letzte Index ist
            sortedList[j - 1] + sortedList[j] / 2.0
        }
    }
}

fun <T : Number> getQuantile(numbersList: List<T>, p: Double): String {
    return roundToThreeDecimalPlaces(calculateQuantile(numbersList, p))
}

fun <T : Number> quartileDifference(numbersList: List<T>): Double {
    return calculateQuantile(numbersList, 0.75) - calculateQuantile(numbersList, 0.25)
}

fun <T : Number> getQuantileDifference(numbersList: List<T>): String {
    return roundToThreeDecimalPlaces(quartileDifference(numbersList))
}


// zu 9
fun calculateVariance(anyList: List<Float>): Double {
    // Wenn weniger als zwei Werte vorhanden sind, ist die Varianz 0
    if (anyList.size <= 1) {
        return 0.0
    }
    val sum = anyList.sum()
    val average = sum / anyList.size

    // Konvertiere jeden Float zu Double vor der Summierung
    val sumOfSquaredDifferences = anyList.sumOf { (it - average).toDouble().pow(2) }
    // Teile durch n-1, um die Stichprobenvarianz zu bekommen
    // Konvertiere das Ergebnis zurück zu Float
    return (sumOfSquaredDifferences / (anyList.size - 1))
}

fun getVariance(anyList: List<Float>): String {

    return roundToThreeDecimalPlaces(calculateVariance(anyList))
}

//zu 10
fun calculateStandardDeviation(anyList: List<Float>): Double {

    return kotlin.math.sqrt(calculateVariance(anyList))
}

fun getStandardDeviation(anyList: List<Float>): String {

    return roundToThreeDecimalPlaces(calculateStandardDeviation(anyList))
}

//zu 11
fun calculateWingSpan(anyList: List<Float>): Float {
    val maxElement = anyList.maxOrNull()
    val minElement = anyList.minOrNull()

    // If either maxElement or minElement is null, the list was empty, so return 0.0.
    if (maxElement == null || minElement == null) {
        return 0.0f
    }

    return maxElement - minElement
}

fun getWingSpan(anyList: List<Float>): String {

    return roundToThreeDecimalPlaces(calculateWingSpan(anyList))
}

//zu 12
fun coefficientOfVariation(anyList: List<Float>): Double {

    if (anyList.isEmpty()) {

        return 0.0
    }
    val StandardDeviation = calculateStandardDeviation(anyList)
    val sum = anyList.sum()
    val average = sum / anyList.size

    return StandardDeviation / average

}

fun getCoefficientOfVariation(anyList: List<Float>): String {

    return roundToThreeDecimalPlaces(coefficientOfVariation(anyList))
}