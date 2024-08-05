package dk.example.notesapp.domain.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: String,
    val title: String,
    private val note: String
) {

    var noteState by mutableStateOf(note)
}