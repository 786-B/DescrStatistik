package com.example.deskrstatistik.UI_Elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deskrstatistik.Utility.FractionBuilder
import com.example.deskrstatistik.Utility.getHeadlineDEActiveColor
import com.example.deskrstatistik.Utility.getQuantile
import com.example.deskrstatistik.Utility.isQuantileCalculable
import com.example.deskrstatistik.Utility.isWholeNumber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(initialValue: String, onNumbersChange: (String) -> Unit) {

    var text by remember {
        mutableStateOf(initialValue)
    }
    val isInvalidNumber = text.toFloatOrNull()?.let { it >= 10000.000f } == true

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        modifier = Modifier.padding(5.dp),
        label = { Text("Enter here", color = Color.LightGray) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onNumbersChange(text)
                text = ""
            }
        ),
        textStyle = TextStyle(
            color = if (isInvalidNumber) Color.Red else Color.White,
            textDecoration = if (isInvalidNumber) TextDecoration.LineThrough else TextDecoration.None
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Cyan.copy(alpha = 0.7f),
            textColor = if (isInvalidNumber) Color.Red else Color.White,
            placeholderColor = Color.White.copy(alpha = 0.4f),
            cursorColor = Color.LightGray
        )
    )
}

@Composable
fun getClearIcon(enabled: Boolean = false, onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Outlined.Delete, // Replace with your desired icon
        contentDescription = "Clear", // Always set a content description for accessibility
        modifier = Modifier
            .size(41.dp)
            .clickable { onClick() }, // Optional padding between icon and text
        tint = if (enabled) Color.Magenta.copy(alpha = 0.7f) else Color.White.copy(alpha = 0.3f)
    )
}

@Composable
fun getCheckIcon(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Outlined.Check, // Replace with your desired icon
        contentDescription = "Check", // Always set a content description for accessibility
        modifier = Modifier
            .size(31.dp)
            .padding(start = 7.dp)
            .clickable { onClick() }, // Optional padding between icon and text
        tint = Color.Green.copy(alpha = 0.7f)
    )
}

@Composable
fun getFalseIcon(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Outlined.Block,
        contentDescription = "Block", // Always set a content description for accessibility
        modifier = Modifier
            .size(25.dp)
            .padding(start = 7.dp)
            .clickable { onClick() }, // Optional padding between icon and text
        tint = Color.Red.copy(alpha = 0.7f)
    )
}

@Composable
fun getFormulaText(text: String, fontSize: Int = 15, color: Color = Color.LightGray) {

    return Text(text = text, color = color, fontSize = fontSize.sp)
}
@Composable
fun getHeadline(text: String, fontSize: Int = 15, color: Color = Color.LightGray) {

    return Text(text = text, color = color, fontSize = fontSize.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
}

@Composable
fun CharWithLowerChar(x: String, i: String, fontsizeX: Int = 18, fontsizeY: Int = 11, color: Color = Color.LightGray) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = x,
            fontSize = fontsizeX.sp,
            fontFamily = FontFamily.Monospace,
            color = color
        )

        // Subscript (i=1)
        Text(
            text = i,
            modifier = Modifier.paddingFromBaseline(bottom = 3.dp),
            fontSize = fontsizeY.sp,
            fontFamily = FontFamily.Monospace,
            color = color
        )
    }
}

@Composable
fun CharWithHigherLowerSymbol(
    symbol: String = "∑",
    n: String = "n",
    i: String = "i=1",
    fontsize: Int = 30,
    bottomFromBaselineN: Int = 11,
    height: Int = 23
) {
    Row(verticalAlignment = Alignment.Bottom) {

        // Summation symbol
        Text(
            text = symbol,
            //to fit with the other part of the formula
            modifier = Modifier.padding(bottom = 9.dp),
            fontSize = fontsize.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.LightGray
        )
        Column {

            // Superscript (n)
            Text(
                text = n,
                //bottom alignment depends on "i=1"
                modifier = Modifier.paddingFromBaseline(bottom = bottomFromBaselineN.dp),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )

            // Subscript (i=1)
            Text(
                text = i,
                //to make it fit
                modifier = Modifier.height(height.dp),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun getQuantilElements(numbersList: List<Float>, quantile: Double, quantileText: String) {
    val isQuantileX = isQuantileCalculable(numbersList, quantile)
    Row(verticalAlignment = Alignment.CenterVertically) {
        getHeadline(
            text = "$quantileText-Quantil", color = getHeadlineDEActiveColor(numbersList, quantile)
        )
        if (isQuantileX) {
            getCheckIcon {
            }
        } else {
            getFalseIcon {
            }
        }
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        getFormulaText(text = "p < 1", color = Color.Green.copy(alpha = 0.8f))
        getFormulaText(text = " & ", color = Color.White.copy(alpha = 0.8f))
        if (isQuantileX) {
            getFormulaText(text = "p ≥ 1/n", color = Color.Green.copy(alpha = 0.8f))
        } else {
            getFormulaText(text = "p < 1/n", color = Color.Red.copy(alpha = 0.8f))
        }
    }
    if (isQuantileX) {
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "q", i = quantileText)
            getFormulaText(text = " = $quantileText * ${numbersList.size} = ")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            val q0x = getQuantile(numbersList, quantile)
            if (isWholeNumber(quantile * numbersList.size)) {
                FractionBuilder(
                    numerator = "1",
                    denominator = "2",
                    lineHeight = 14,
                    fontSizeNumerator = 13,
                    fontSizeDenominator = 13
                )
                getFormulaText(" (x(${numbersList.size * quantile}) + x(${numbersList.size * quantile}+1)) = ")
                getFormulaText("$q0x", color = Color.Green.copy(alpha = .8f))

            } else {
                getFormulaText(text = "x⌈${numbersList.size * quantile}⌉ = ")
                getFormulaText(text = "$q0x", color = Color.Green.copy(alpha = .8f))
            }
        }
    }
}