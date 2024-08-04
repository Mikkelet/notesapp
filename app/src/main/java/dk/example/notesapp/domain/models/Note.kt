package dk.example.notesapp.domain.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Note(
    val id: String,
    private val title: String,
    private val note: String
) {

    var titleState by mutableStateOf(title)
    var noteState by mutableStateOf(note)
}