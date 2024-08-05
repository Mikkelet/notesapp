package dk.example.notesapp.domain.usecases

import dagger.hilt.android.scopes.ViewModelScoped
import dk.example.notesapp.data.local.NotesDatabase
import dk.example.notesapp.domain.mappers.NotesMapper.toNotes
import dk.example.notesapp.domain.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class ObserveNotesUseCase @Inject constructor(
    private val notesDatabase: NotesDatabase
) {
    fun launch(): Flow<List<Note>> {
        return notesDatabase.notesDao().observeAll().map { it.toNotes() }
    }
}