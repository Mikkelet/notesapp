package dk.example.notesapp.presentation.screens.edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.InsertNoteUseCase
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import dk.example.notesapp.presentation.SavedStateKey
import dk.example.notesapp.presentation.SavedStateValDelegate
import dk.example.notesapp.presentation.SavedStateVarDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertNoteUseCase: InsertNoteUseCase,
    observeNoteUseCase: ObserveNoteUseCase,
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class OnNote(val note: Note) : UiState()
    }

    var title: String by SavedStateVarDelegate(savedStateHandle, SavedStateKey.KEY_TITLE, "")
    val titleFlow = savedStateHandle.getStateFlow(SavedStateKey.KEY_TITLE, "")
    val id: String by SavedStateValDelegate(savedStateHandle, SavedStateKey.KEY_ID)
    val uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    private val noteFlow: Flow<Note> = observeNoteUseCase.launch(id)

    fun init() {
        viewModelScope.launch {
            noteFlow.collect {
                uiState.emit(UiState.OnNote(it))
                if (title.isEmpty()) onTitleUpdate(it.title)
            }
        }
    }

    fun applyChanges() {
        viewModelScope.launch {
            val uiState = uiState.value
            if (uiState is UiState.OnNote) {
                val updatedNote = uiState.note.copy(title = title)
                insertNoteUseCase.launch(updatedNote)
            }
        }
    }

    fun onTitleUpdate(text: String) {
        title = text
    }
}