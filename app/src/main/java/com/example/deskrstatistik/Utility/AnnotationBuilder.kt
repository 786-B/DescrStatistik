package com.example.deskrstatistik.Utility

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//annotated strings
@Composable
fun FractionBuilder(
    numerator: String,
    denominator: String,
    fontSizeNumerator: Int = 15,
    fontSizeDenominator: Int = 15,
    lineHeight: Int = 12
) {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = lineHeight.sp)) {
                withStyle(
                    style = SpanStyle(
                        fontSize = fontSizeNumerator.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(numerator + "\n")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = fontSizeDenominator.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace
                    )
                ) {
                    append(denominator)
                }
            }
        }
    )
}
