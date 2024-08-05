package dk.example.notesapp.presentation.screens.edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.InsertNoteUseCase
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class EditNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val observeNoteUseCase: ObserveNoteUseCase,
) : ViewModel() {

    var noteState: Note? by mutableStateOf(null)

    private val noteFlow: Flow<Note?> = savedStateHandle
        .getStateFlow("id", "")
        .flatMapMerge {
            observeNoteUseCase.launch(it)
        }

    fun init() {
        viewModelScope.launch {
            noteFlow.collect {
                if (it != null)
                    noteState = it
                else println("qqq note is null")
            }
        }
    }

    fun applyChanges() {
        viewModelScope.launch {
            if (noteState != null) insertNoteUseCase.launch(noteState!!)
        }
    }

}