package com.example.notesultimate.ui.screens.add_edit_notes


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesultimate.ui.theme.NotesUltimateTheme
import com.example.notesultimate.ui.theme.Roboto
import com.example.notesultimate.ui.util.Previews


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TransparentTextField(
    modifier:Modifier = Modifier,
    text:String,
    placeholder: String ,
    background: Color = Color.Transparent,
    singleLine:Boolean = false,
    maxLines:Int = 4,
    onTextChanged: (String) -> Unit,
    fontSize:TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    cornerRadius:Dp = 12.dp
    ) {

    var query by remember{ mutableStateOf(text) }
    val keyBoard = LocalSoftwareKeyboardController.current

    TextField(
        value = query,
        onValueChange = {
            query = it
            onTextChanged(it) },
        singleLine = singleLine,
        maxLines = maxLines,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = Roboto,
                fontSize = fontSize,
                color = Color.Black.copy(alpha = .6f),
                fontWeight = fontWeight
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = background,
            cursorColor = Color.Black,
            focusedIndicatorColor = background,
            unfocusedIndicatorColor = background,
        ),
        textStyle = TextStyle(
            fontFamily = Roboto,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = Color.Black
        ),
//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//        keyboardActions = KeyboardActions(onDone = {
//            keyBoard?.hide()
//        }),
        modifier = modifier
            .background(color = background,shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
    )
}


