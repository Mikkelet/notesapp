package dk.example.notesapp.presentation.screens.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val observeNoteUseCase: ObserveNoteUseCase,
) : ViewModel() {

    val uiState = MutableStateFlow<UiState>(UiState.Loading)

    fun init(){
        viewModelScope.launch {
            noteFlow.collect {
                println("qqq note update, $it")
                uiState.emit(UiState.OnNote(it))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val noteFlow: Flow<Note> = savedStateHandle
        .getStateFlow("id", "")
        .flatMapMerge { observeNoteUseCase.launch(it) }

    sealed class UiState {
        data object Loading : UiState()
        data class OnNote(val note: Note) : UiState()
    }
}