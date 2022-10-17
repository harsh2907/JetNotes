package com.example.notesultimate.ui.screens.add_edit_notes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.notesultimate.ui.theme.*
import com.example.notesultimate.ui.util.Previews





@Composable
fun ColorSelectionRow(
    selectedColor:Color,
    onSelect:(Color)->Unit
) {
    var currentColor by remember{ mutableStateOf(selectedColor) }
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)) {
        items(colorList.size){index ->
            val color = colorList[index]
            ColorBall(
                color = color,
                isSelected = color == currentColor
            ){
                currentColor = it
                onSelect(it)
            }
        }
    }
}

@Composable
fun ColorBall(
    color: Color,
    isSelected:Boolean,
    onClick:(Color)->Unit
) {
    Spacer(modifier = Modifier.padding(4.dp))

    Box(modifier = Modifier
        .size(40.dp)
        .border(
            width = if (isSelected) 2.dp else 0.dp,
            color = if (isSelected) Color.Black else Color.Transparent,
            shape = CircleShape
        )
        .clip(CircleShape)
        .background(color)
        .clickable {
            onClick(color)
        }

    )
    Spacer(modifier = Modifier.padding(3.dp))
}