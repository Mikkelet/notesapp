package dk.example.notesapp.presentation.screens.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import dk.example.notesapp.presentation.screens.Screen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeNoteUseCase: ObserveNoteUseCase,
) : ViewModel() {
    sealed class UiState {
        data object Loading : UiState()
        data class OnNote(val note: Note) : UiState()
    }

    private val args = savedStateHandle.toRoute<Screen.NoteScreen>()
    val uiState = observeNoteUseCase.launch(args.id).map {
        UiState.OnNote(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState.Loading
    )
}