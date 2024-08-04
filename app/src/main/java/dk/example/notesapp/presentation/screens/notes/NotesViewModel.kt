package dk.example.notesapp.presentation.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.InsertNoteUseCase
import dk.example.notesapp.domain.usecases.ObserveNotesUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    observeNotesUseCase: ObserveNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val notes = (0..10).map { Note("$it", "", "") }
            notes.forEach {
                insertNoteUseCase.launch(it)
            }
        }
    }

    val notesFlow: StateFlow<List<Note>> = observeNotesUseCase.launch()
}