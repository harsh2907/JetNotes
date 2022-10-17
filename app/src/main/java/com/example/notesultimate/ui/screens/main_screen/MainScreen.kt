package com.example.notesultimate.ui.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.domain.util.NoteOrder
import com.example.notesultimate.domain.util.OrderType
import com.example.notesultimate.ui.screens.navigation.Screens
import com.example.notesultimate.ui.screens.viewmodel.NotesViewModel
import com.example.notesultimate.ui.theme.NotesUltimateTheme
import com.example.notesultimate.ui.theme.QuickSand
import com.example.notesultimate.ui.util.NotesEvent
import com.example.notesultimate.ui.util.Previews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    navController:NavHostController,
    viewModel: NotesViewModel
) {

    LaunchedEffect(key1 = true){
        viewModel.setNote(null)
    }
    val scaffoldState = rememberScaffoldState()
    var isClick by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value
    val notes = state.notes


    val animate = animateFloatAsState(
        targetValue = if(isClick) 405f else 0f,
        animationSpec = tween(
            durationMillis = 700,
            easing = LinearOutSlowInEasing
        )
    )
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isClick = !isClick
                    navController.navigate(Screens.AddEditScreen.route)
                },
                backgroundColor =
                if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier.graphicsLayer(rotationZ = animate.value)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "add",
                    tint = if (!isSystemInDarkTheme()) Color.White else Color.Black
                )
            }
        },
        topBar = {
            TopBarMainScreen(viewModel)
        }

    )
    { padding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            items(notes.size) { index ->
                NoteItem(note = notes[index], onDelete = {
                    viewModel.onEvent(NotesEvent.DeleteNote(it))
                    scope.launch{
                      val result =   scaffoldState.snackbarHostState.showSnackbar(
                            "Note deleted successfully",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Long
                        )
                       if(result == SnackbarResult.ActionPerformed){
                            viewModel.onEvent(NotesEvent.RestoreNote)
                        }
                    }
                }){ note->
                    viewModel.setNote(note)
                    navController.navigate(Screens.AddEditScreen.route)
                }
            }
        }
    }
}



@Composable
fun TopBarMainScreen(
    viewModel: NotesViewModel
) {
    var searchQuery by remember{ mutableStateOf("") }
    val showSearchBar = rememberSaveable { mutableStateOf(false) }
    val notes = viewModel.state.collectAsState().value.notes
    val scope = rememberCoroutineScope()

    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        HideableSearchBar(
            text = searchQuery,
            isSearchActive = showSearchBar.value,
            onTextChange = {
                searchQuery= it

                if(searchQuery.isNotEmpty()){
                    viewModel.searchNotes(searchQuery.trim())
                }
                else
                    viewModel.getNotes(NoteOrder.Date(OrderType.Descending))
            },
            onSearchClick = {
                showSearchBar.value = !showSearchBar.value
            },
            onCloseClick = {
                showSearchBar.value = !showSearchBar.value
            },
            modifier = Modifier.fillMaxWidth()
        )


    }
}