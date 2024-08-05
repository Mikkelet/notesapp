package dk.example.notesapp.presentation.screens.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import dk.example.notesapp.presentation.SavedStateKey
import dk.example.notesapp.presentation.SavedStateValDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeNoteUseCase: ObserveNoteUseCase,
) : ViewModel() {

    val uiState = MutableStateFlow<UiState>(UiState.Loading)
    private val id: String by SavedStateValDelegate<String>(savedStateHandle, SavedStateKey.KEY_ID)
    private val noteFlow: Flow<Note> = observeNoteUseCase.launch(id)

    fun init() {
        viewModelScope.launch {
            noteFlow.collect {
                uiState.emit(UiState.OnNote(it))
            }
        }
    }


    sealed class UiState {
        data object Loading : UiState()
        data class OnNote(val note: Note) : UiState()
    }
}