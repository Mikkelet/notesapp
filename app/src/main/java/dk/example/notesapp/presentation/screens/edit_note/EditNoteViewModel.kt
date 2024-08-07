package dk.example.notesapp.presentation.screens.edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.InsertNoteUseCase
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import dk.example.notesapp.presentation.SavedStateKey
import dk.example.notesapp.presentation.SavedStateVarDelegate
import dk.example.notesapp.presentation.navigation.NavigationState
import dk.example.notesapp.presentation.navigation.Screen
import dk.example.notesapp.presentation.screens.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val navigationState: NavigationState,
    observeNoteUseCase: ObserveNoteUseCase,
) : BaseViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class OnNote(val note: Note) : UiState()
    }

    private val args = savedStateHandle.toRoute<Screen.EditNoteScreen>()
    var title: String by SavedStateVarDelegate(savedStateHandle, SavedStateKey.KEY_TITLE, "")
    val titleFlow = savedStateHandle.getStateFlow(SavedStateKey.KEY_TITLE, "")
    val uiState = observeNoteUseCase.launch(args.id)
        .map { UiState.OnNote(it) }
        .stateIn(UiState.Loading)

    fun applyChanges() {
        viewModelScope.launch {
            val uiState = uiState.value
            if (uiState is UiState.OnNote) {
                val updatedNote = uiState.note.copy(title = title)
                insertNoteUseCase.launch(updatedNote)
                navigationState.pop()
            }
        }
    }

    fun onTitleUpdate(text: String) {
        title = text
    }
}