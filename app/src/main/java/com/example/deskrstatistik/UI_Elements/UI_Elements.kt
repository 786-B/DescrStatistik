package com.example.deskrstatistik.UI_Elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
            focusedBorderColor = Color.Blue,
            textColor = Color.White,
            placeholderColor = Color.White.copy(alpha = 0.4f),
            cursorColor = Color.LightGray
        )
    )
}

@Composable
fun getButton(enabled: Boolean = false, onClick: () -> Unit) {

    Icon(
        imageVector = Icons.Outlined.Delete, // Replace with your desired icon
        contentDescription = "Clear", // Always set a content description for accessibility
        modifier = Modifier.size(41.dp).clickable { onClick() }, // Optional padding between icon and text
        tint = if(enabled) Color.Magenta.copy(alpha = 0.7f) else Color.White.copy(alpha = 0.3f)
    )
   /* ElevatedButton(
        onClick = { onClick() },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.White.copy(alpha = 0.5f), contentColor = Color.Black,
            disabledContainerColor = Color.White.copy(alpha = 0.2f),
            disabledContentColor = Color.White.copy(alpha = 0.2f)
        ),
        enabled = enabled,
        border = BorderStroke(3.dp, color = if (enabled) Color.Blue else Color.Transparent)
    ) {
            Icon(
                imageVector = Icons.Filled.Delete, // Replace with your desired icon
                contentDescription = "Clear", // Always set a content description for accessibility
                //modifier = Modifier., // Optional padding between icon and text
                tint = Color.Red
            )

    }*/
}