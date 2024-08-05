package dk.example.notesapp.data.local

import dk.example.notesapp.domain.models.Note
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesDatabase @Inject constructor() {
    val notesFlow = MutableStateFlow<List<Note>>(emptyList())
}

