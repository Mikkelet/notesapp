package dk.example.notesapp.domain.usecases

import dk.example.notesapp.data.local.NotesDatabase
import dk.example.notesapp.domain.mappers.NotesMapper.toEntities
import dk.example.notesapp.domain.models.Note
import javax.inject.Inject

class InsertDummyDataUseCase @Inject constructor(
    private val notesDatabase: NotesDatabase,
) {
    suspend fun launch(notes: List<Note>) {
        val entities = notesDatabase.notesDao().getAll()
        if (entities.isEmpty()) notesDatabase.notesDao().insert(notes.toEntities())
    }
}