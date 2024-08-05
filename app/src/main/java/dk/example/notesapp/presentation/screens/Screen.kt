package dk.example.notesapp.presentation.screens

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object NotesScreen : Screen()
    @Serializable
    data class NoteScreen(val id: String) : Screen()
    @Serializable
    data class EditNoteScreen(val id: String) : Screen()
}