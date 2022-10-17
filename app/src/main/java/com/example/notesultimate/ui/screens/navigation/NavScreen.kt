package com.example.notesultimate.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesultimate.ui.screens.add_edit_notes.AddEditScreen
import com.example.notesultimate.ui.screens.main_screen.MainScreen
import com.example.notesultimate.ui.screens.viewmodel.NotesViewModel

@Composable
fun NavScreen(viewModel: NotesViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.MainScreen.route ){
        composable(
            route = Screens.MainScreen.route
        ){
            MainScreen(navController,viewModel)
        }
        composable(
            route = Screens.AddEditScreen.route
        ){
            AddEditScreen(navController = navController, noteViewModel = viewModel)
        }
    }
}