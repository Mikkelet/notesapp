package dk.example.notesapp.presentation.screens.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.example.notesapp.presentation.screens.edit_note.EditNoteScreen
import dk.example.notesapp.presentation.screens.edit_note.EditNoteViewModel
import dk.example.notesapp.presentation.screens.note.NoteScreen
import dk.example.notesapp.presentation.screens.note.NoteViewModel
import dk.example.notesapp.presentation.screens.notes.NotesScreen

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "notes",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }) {
        composable("notes") {
            NotesScreen(navController)
        }
        composable("note/{id}", arguments = listOf(navArgNote)) {
            val viewModel: NoteViewModel = hiltViewModel(it)
            NoteScreen(navigation = navController)
        }
        composable("editNote/{id}", arguments = listOf(navArgNote)) {
            val viewModel: EditNoteViewModel = hiltViewModel(it)
            viewModel.init()
            EditNoteScreen(navigation = navController)
        }
    }
}

val navArgNote = navArgument("id") {
    type = NavType.StringType
    defaultValue = ""
}
