package com.example.notesultimate.ui.screens.add_edit_notes

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.ui.screens.viewmodel.NotesViewModel
import com.example.notesultimate.ui.theme.NotesUltimateTheme
import com.example.notesultimate.ui.theme.colorList
import com.example.notesultimate.ui.util.NotesEvent
import com.example.notesultimate.ui.util.Previews
import kotlinx.coroutines.launch


@Composable
fun AddEditScreen(
    noteViewModel:NotesViewModel ,
    navController: NavController
) {
    val currentNote = noteViewModel.note.collectAsState().value
    val noteBackground = remember {
        Animatable(
            Color(
                currentNote?.color ?: colorList.random().toArgb()
            )
        )
    }
    val scope = rememberCoroutineScope()

    val title = rememberSaveable{ mutableStateOf(
        currentNote?.title ?: ""
    ) }

    val content = rememberSaveable{ mutableStateOf(
        currentNote?.content ?: ""
    ) }


    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {

                    if(currentNote==null){
                        val note =  Note(
                            title = title.value,
                            content = content.value,
                            color = noteBackground.value.toArgb()
                        )
                        noteViewModel.onEvent(NotesEvent.InsertNote(note))
                    }

                    currentNote?.let {
                        it.title = title.value
                        it.content = content.value
                        it.color = noteBackground.value.toArgb()
                        noteViewModel.onEvent(NotesEvent.UpdateNote(it))
                    }

                    noteViewModel.setNote(null)
                    navController.navigateUp()
                }
            },
                backgroundColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription ="Save",
                    tint = Color.Black
                )
            }
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = noteBackground.value)
                .padding(paddingValues)
        ) {

            ColorSelectionRow(selectedColor = noteBackground.value){
                scope.launch {
                    noteBackground.animateTo(
                        targetValue = Color(it.toArgb()),
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.padding(12.dp))
            TransparentTextField(
                text = title.value,
                placeholder = "Choose a title...",
                onTextChanged = {
                    title.value = it
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )

            TransparentTextField(
                text = content.value,
                placeholder = "Enter content...",
                onTextChanged = {
                    content.value = it
                },
                maxLines = 12,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)

            )
        }
    }
}


