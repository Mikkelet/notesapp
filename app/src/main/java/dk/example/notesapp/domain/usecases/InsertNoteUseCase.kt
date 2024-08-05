package dk.example.notesapp.domain.usecases

import dk.example.notesapp.data.local.NotesDatabase
import dk.example.notesapp.domain.mappers.NotesMapper.toEntity
import dk.example.notesapp.domain.models.Note
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val notesDatabase: NotesDatabase,
) {
    suspend fun launch(note: Note) {
        notesDatabase.notesDao().insert(note.toEntity())
    }
}