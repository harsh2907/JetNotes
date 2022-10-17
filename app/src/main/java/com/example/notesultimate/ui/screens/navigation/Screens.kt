package com.example.notesultimate.ui.screens.navigation

sealed class Screens(val route:String) {
    object MainScreen:Screens("main_screen")
    object AddEditScreen:Screens("add_edit_screen")
}