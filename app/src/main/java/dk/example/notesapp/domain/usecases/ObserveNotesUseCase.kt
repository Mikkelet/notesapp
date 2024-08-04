package dk.example.notesapp.domain.usecases

import dagger.hilt.android.scopes.ViewModelScoped
import dk.example.notesapp.data.local.NotesDatabase
import dk.example.notesapp.domain.models.Note
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ViewModelScoped
class ObserveNotesUseCase @Inject constructor(
    private val notesDatabase: NotesDatabase
) {

    fun launch(): StateFlow<List<Note>> {
        return notesDatabase.notesFlow
    }
}