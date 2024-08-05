package dk.example.notesapp.presentation.screens.edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.InsertNoteUseCase
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import dk.example.notesapp.presentation.SavedStateKey
import dk.example.notesapp.presentation.SavedStateVarDelegate
import dk.example.notesapp.presentation.screens.Screen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    private val args = savedStateHandle.toRoute<Screen.EditNoteScreen>()
    var title: String by SavedStateVarDelegate(savedStateHandle, SavedStateKey.KEY_TITLE, "")
    val titleFlow = savedStateHandle.getStateFlow(SavedStateKey.KEY_TITLE, "")
    val uiState = observeNoteUseCase.launch(args.id)
        .map { UiState.OnNote(it) }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState.Loading
    )

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