package dk.example.notesapp.presentation.screens

import dk.example.notesapp.domain.models.Note
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object NotesScreen : Screen()
    @Serializable
    data class NoteScreen(val note: Note) : Screen()
    @Serializable
    data class EditNoteScreen(val note: Note) : Screen()
}