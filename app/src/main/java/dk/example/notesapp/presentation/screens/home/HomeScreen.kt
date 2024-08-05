package dk.example.notesapp.presentation.screens.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dk.example.notesapp.presentation.screens.Screen
import dk.example.notesapp.presentation.screens.edit_note.EditNoteScreen
import dk.example.notesapp.presentation.screens.edit_note.EditNoteViewModel
import dk.example.notesapp.presentation.screens.note.NoteScreen
import dk.example.notesapp.presentation.screens.note.NoteViewModel
import dk.example.notesapp.presentation.screens.notes.NotesScreen
import dk.example.notesapp.presentation.screens.notes.NotesViewModel

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.NotesScreen,
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
        composable<Screen.NotesScreen> {
            val viewModel: NotesViewModel = hiltViewModel(it)
            NotesScreen(navController)
        }
        composable<Screen.NoteScreen> {
            val viewModel: NoteViewModel = hiltViewModel(it)
            NoteScreen(navigation = navController)
        }
        composable<Screen.EditNoteScreen> {
            val viewModel: EditNoteViewModel = hiltViewModel(it)
            EditNoteScreen(navigation = navController)
        }
    }
}