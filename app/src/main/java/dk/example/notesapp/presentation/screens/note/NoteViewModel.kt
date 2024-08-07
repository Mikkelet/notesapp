package dk.example.notesapp.presentation.screens.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import dk.example.notesapp.presentation.navigation.NavigationState
import dk.example.notesapp.presentation.navigation.Screen
import dk.example.notesapp.presentation.screens.base.BaseViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeNoteUseCase: ObserveNoteUseCase,
    private val navigationState: NavigationState,
) : BaseViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class OnNote(val note: Note) : UiState()
    }

    private val args = savedStateHandle.toRoute<Screen.NoteScreen>()
    val uiState = observeNoteUseCase.launch(args.id).map {
        UiState.OnNote(it)
    }.stateIn(UiState.Loading)

    fun onEditClicked() {
        viewModelScope.launch {
            navigationState.push(Screen.EditNoteScreen(args.id))
        }
    }
}