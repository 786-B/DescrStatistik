package com.example.deskrstatistik.Utility

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round


fun trimZeros(input: String): String {
    return try {
        BigDecimal(input).stripTrailingZeros().toPlainString()
    } catch (e: NumberFormatException) {
        input // Return the original input if parsing fails
    }
}

fun roundToThreeDecimalPlaces(number: Float): String {
    val roundedNumber = round(number * 1000) / 1000
    return "%.3f".format(roundedNumber).replace('.', ',')
}

fun roundToThreeDecimalPlacesDouble(average: Double): Double {
    return BigDecimal(average).setScale(3, RoundingMode.HALF_EVEN).toDouble()
}

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

fun List<Float>.median(): String {
    if (isEmpty()) return "0.0"

    return if (size % 2 == 1) {
        roundToThreeDecimalPlaces(this[(size - 1) / 2])
    } else {
        roundToThreeDecimalPlaces((this[size / 2 - 1] + this[size / 2]) / 2.0f)
    }
}

@Composable
fun getFormulaText(text: String, fontSize: Int = 20) {

    return Text(text = text, color = Color.LightGray, fontSize = fontSize.sp)
}

fun List<Float>.modes(): String {
    val frequencyMap = groupingBy { it }.eachCount()
    val maxFrequency = frequencyMap.maxOfOrNull { it.value } ?: return "0.0"

    return frequencyMap.filter { it.value == maxFrequency }.keys.toList().toString()
}

//annotated strings
@Composable
fun CharWithLowerChar(x: String, i: String) {
    Text(buildAnnotatedString {

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = x,
                modifier = Modifier.paddingFromBaseline(bottom = 9.dp),
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )

            // Subscript (i=1)
            Text(
                text = i,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )
        }
    }, color = Color.LightGray)
}

@Composable
fun SummationSymbol() {
    Row(verticalAlignment = Alignment.Bottom) {

        // Summation symbol
        Text(
            text = "∑",
            //to fit with the other part of the formula
            modifier = Modifier.padding(bottom = 9.dp),
            fontSize = 30.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.LightGray
        )
        Column {

            // Superscript (n)
            Text(
                text = "n",
                //bottom alignment depends on "i=1"
                modifier = Modifier.paddingFromBaseline(bottom = 11.dp),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )

            // Subscript (i=1)
            Text(
                text = "i=1",
                //to make it fit
                modifier = Modifier.height(23.dp),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun fractionBuilder(
    x: String,
    y: String,
    fontSizeX: Int = 15,
    fontSizeY: Int = 15,
    lineheight: Int = 12
) {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = lineheight.sp)) {
                withStyle(
                    style = SpanStyle(
                        fontSize = fontSizeX.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(x + "\n")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = fontSizeY.sp, color = Color.LightGray,
                        fontFamily = FontFamily.Monospace
                    )
                ) {
                    append(y)
                }
            }
        }
    )
}

