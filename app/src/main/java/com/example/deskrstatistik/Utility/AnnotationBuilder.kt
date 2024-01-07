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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//annotated strings
@Composable
fun CharWithLowerChar(x: String, i: String, fontsizeX: Int = 18, fontsizeY: Int= 11) {
    Text(buildAnnotatedString {

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = x,
                modifier = Modifier.paddingFromBaseline(bottom = 9.dp),
                fontSize = fontsizeX.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )

            // Subscript (i=1)
            Text(
                text = i,
                fontSize = fontsizeY.sp,
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