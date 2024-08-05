package dk.example.notesapp.presentation.screens.edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.InsertNoteUseCase
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import dk.example.notesapp.presentation.SavedStateHandleValues.editTitle
import dk.example.notesapp.presentation.SavedStateHandleValues.editTitleFlow
import dk.example.notesapp.presentation.SavedStateHandleValues.idFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class EditNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val observeNoteUseCase: ObserveNoteUseCase,
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class OnNote(val note: Note) : UiState()
    }

    val titleFlow = savedStateHandle.editTitleFlow()
    val uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    private val noteFlow: Flow<Note> = savedStateHandle.idFlow()
        .flatMapMerge { observeNoteUseCase.launch(it) }

    fun init() {
        viewModelScope.launch {
            noteFlow.collect {
                uiState.emit(UiState.OnNote(it))
                if (savedStateHandle.editTitle.isEmpty()) onTitleUpdate(it.title)
            }
        }
    }

    fun applyChanges() {
        viewModelScope.launch {
            val uiState = uiState.value
            if (uiState is UiState.OnNote) {
                val updatedNote = uiState.note
                    .copy(title = savedStateHandle.editTitle)
                insertNoteUseCase.launch(updatedNote)
            }
        }
    }

    fun onTitleUpdate(text: String) {
        savedStateHandle.editTitle = text
    }
}