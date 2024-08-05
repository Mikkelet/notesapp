package dk.example.notesapp.presentation.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.InsertDummyDataUseCase
import dk.example.notesapp.domain.usecases.ObserveNotesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    observeNotesUseCase: ObserveNotesUseCase,
    private val insertDummyDataUseCase: InsertDummyDataUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                val notes = (0..10).map { Note("$it", "", "") }
                insertDummyDataUseCase.launch(notes)
            } catch (e: Exception) {
                println("qqq $e")
            }
        }
    }

    val notesFlowSorted: StateFlow<List<Note>> = observeNotesUseCase.launch()
        .map { notes ->
            notes.sortedBy { it.id.toInt() }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
}