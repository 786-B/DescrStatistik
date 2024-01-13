package com.example.deskrstatistik.Utility

import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt


fun roundToThreeDecimalPlaces(number: Number): String {
    val roundedNumber = round(number.toDouble() * 1000) / 1000
    return "%.3f".format(roundedNumber)
}

// zu 5
fun arithmeticMean(anyList: List<Float>): Float {
    if (anyList.isEmpty()) return 0.0f
    val sum = anyList.sum()

    return sum / anyList.size
}

fun getArithmeticMean(anyList: List<Float>): String {
    val sum = arithmeticMean(anyList)

    return roundToThreeDecimalPlaces(sum)
}

fun getSum(anyList: List<Float>): String {
    val sum = anyList.sum()

    return roundToThreeDecimalPlaces(sum)
}

// zu 6
fun List<Float>.median(): Float {
    if (isEmpty()) return 0.0f

    return if (size % 2 == 1) {
        (this[(size - 1) / 2])
    } else {
        ((this[size / 2 - 1] + this[size / 2]) / 2.0f)
    }
}

fun getMedian(anyList: List<Float>): String {
    return if (anyList.isEmpty()) {
        "0.0"
    } else roundToThreeDecimalPlaces(anyList.median())

}


// zu 7
fun List<Float>.modes(): List<Float> {
    val frequencyMap = groupingBy { it }.eachCount()
    val maxFrequency = frequencyMap.maxOfOrNull { it.value } ?: return emptyList()

    return if (maxFrequency > 1) {
        frequencyMap.filter { it.value == maxFrequency }.keys.toList()
    } else emptyList()
}

fun getModes(anyList: List<Float>): String {
    return if (anyList.modes().isEmpty()) {
        "0.0"
    } else anyList.modes().toString()

}

// zu 8
//Definition
fun <T : Number> isQuantileCalculable(numbersList: List<T>, p: Double): Boolean {
    val n = numbersList.size

    // Überprüft, ob p im gültigen Bereich liegt
    if (p <= 0.0 || p >= 1.0) {
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

    if (numbersList.isEmpty()) {
        return 0.0
    }
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

    if (anyList.size <= 1) {
        return 0.0
    }
    val sum = anyList.sum()
    val average = sum / anyList.size

    val sumOfSquaredDifferences = anyList.sumOf { (it - average).toDouble().pow(2) }

    return (sumOfSquaredDifferences / (anyList.size - 1))
}

fun getVariance(anyList: List<Float>): String {

    return roundToThreeDecimalPlaces(calculateVariance(anyList))
}

//zu 10
fun calculateStandardDeviation(anyList: List<Float>): Double {

    return sqrt(calculateVariance(anyList))
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

//zu 13
// Berechnung des dritten zentralen Moments
fun thirdCentralMoment(data: List<Float>): Float {
    val mean = arithmeticMean(data)
    return data.map { (it - mean).pow(3) }.sum() / (data.size - 1)
}

// Berechnung von sk1
fun skewness1(data: List<Float>): Double {
    val s = calculateStandardDeviation(data)
    val m3 = thirdCentralMoment(data)
    return m3 / s.pow(3)
}

fun getSkewness1(data: List<Float>): String {
    if (data.isEmpty()) {

        return "0.0"
    }
    return roundToThreeDecimalPlaces(skewness1(data))
}

// Berechnung von sk2
fun skewness2(data: List<Float>): Double {

    if (data.isEmpty()) {
        return 0.0
    }
    val modes = data.modes()
    val mean = arithmeticMean(data)
    val s = calculateStandardDeviation(data)

    // Check if modesList is not empty
    return if (modes.isNotEmpty()) {
        val mode = modes.first() // You can decide how to handle multiple modes if necessary
        (mean - mode) / s
    } else {
        0.0 // Or handle the lack of mode as appropriate for your case
    }
}

fun getSkewness2(data: List<Float>): String {
    if (data.isEmpty()) {

        return "0.0"
    }
    return roundToThreeDecimalPlaces(skewness2(data))
}

// Berechnung von sk3
fun skewness3(data: List<Float>): Double {
    val median = data.median()
    val mean = arithmeticMean(data)
    val s = calculateStandardDeviation(data)
    return (mean - median) / s
}

fun getSkewness3(data: List<Float>): String {
    if (data.isEmpty()) {

        return "0.0"
    }
    return roundToThreeDecimalPlaces(skewness3(data))
}

//zu 14 Phi
fun phiDispersionMeasure(data: List<Int>): Double {
    if (data.isEmpty()) return Double.NaN // Rückgabe NaN, wenn die Liste leer ist

    val frequencyMap = data.groupingBy { it }.eachCount()
    val n = data.size.toDouble()
    val K = frequencyMap.size.toDouble()

    val h_amod = frequencyMap.maxOf { it.value } / n
    val phi_min = 2 * (1 - h_amod)

    val phi_max = frequencyMap.values.sumOf {
        val h_k = it / n
        Math.abs(h_k - 1 / K)
    }

    return phi_min / (phi_min + phi_max)
}

fun getHeadlineDEActiveColor(numbersList: List<Float>, quantile: Double): Color {
    return if(isQuantileCalculable(numbersList, quantile)) Color.LightGray else Color.LightGray.copy(alpha = .5f)
}