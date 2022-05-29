package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@Composable
fun TextFieldCustom(
    text: String,
    textChange: (String) -> Unit,
    labelText: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = textChange,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        maxLines = 1,
        textStyle = TextStyle(
            color = Color.White,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
        ),
        label = { Text(text = labelText, color = Color.White.copy(0.5f)) },
        modifier = modifier
              .background(Color.Transparent)
              .fillMaxSize(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            disabledTextColor = Color(0xFF6B6969),
            backgroundColor = Color(0xFF6B6969),
            focusedIndicatorColor = Color(0xFF6B6969),
            unfocusedIndicatorColor = Color(0xFF6B6969),
            disabledIndicatorColor = Color(0xFF6B6969)
        )
    )
}