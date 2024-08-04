package dk.example.notesapp.presentation.screens.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.domain.models.Note
import dk.example.notesapp.domain.usecases.ObserveNoteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val observeNoteUseCase: ObserveNoteUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val noteFlow: Flow<Note?> = savedStateHandle
        .getStateFlow("id", "")
        .flatMapMerge { observeNoteUseCase.launch(it) }
}