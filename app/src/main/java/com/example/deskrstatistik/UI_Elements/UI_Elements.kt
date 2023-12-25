package com.example.deskrstatistik.UI_Elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(initialValue: String, onNumbersChange: (String) -> Unit) {

    var text by remember {
        mutableStateOf(initialValue)
    }

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
        ), colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Cyan.copy(alpha = 0.7f),
            textColor = Color.White,
            placeholderColor = Color.White.copy(alpha = 0.4f),
            cursorColor = Color.LightGray
        )
    )
}

@Composable
fun getIcon(enabled: Boolean = false, onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Outlined.Delete, // Replace with your desired icon
        contentDescription = "Clear", // Always set a content description for accessibility
        modifier = Modifier.size(41.dp).clickable { onClick() }, // Optional padding between icon and text
        tint = if(enabled) Color.Magenta.copy(alpha = 0.7f) else Color.White.copy(alpha = 0.3f)
    )
}