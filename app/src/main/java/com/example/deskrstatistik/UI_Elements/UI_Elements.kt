package com.example.deskrstatistik.UI_Elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
        modifier = Modifier.size(41.dp).clickable { onClick() }, // Optional padding between icon and text
        tint = if(enabled) Color.Magenta.copy(alpha = 0.7f) else Color.White.copy(alpha = 0.3f)
    )
}

@Composable
fun getQuestionIcon(onClick: () -> Unit){
    Icon(
        imageVector = Icons.Outlined.QuestionMark, // Replace with your desired icon
        contentDescription = "Info", // Always set a content description for accessibility
        modifier = Modifier.size(41.dp).padding(start = 7.dp).clickable { onClick() }, // Optional padding between icon and text
        tint = Color.White.copy(alpha = 0.7f)
    )
}
@Composable
fun getInfoIcon(onClick: () -> Unit){
    Icon(
        imageVector = Icons.Outlined.Info, // Replace with your desired icon
        contentDescription = "Info", // Always set a content description for accessibility
        modifier = Modifier.size(41.dp).padding(start = 7.dp).clickable { onClick() }, // Optional padding between icon and text
        tint = Color.White.copy(alpha = 0.7f)
    )
}

@Composable
fun getFormulaText(text: String, fontSize: Int = 15) {

    return Text(text = text, color = Color.LightGray, fontSize = fontSize.sp)
}