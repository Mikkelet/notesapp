package dk.example.notesapp.domain.mappers

import dk.example.notesapp.data.local.entities.NoteEntity
import dk.example.notesapp.domain.models.Note

object NotesMapper {
    fun NoteEntity.toNote(): Note = Note(id, title, note)
    fun List<NoteEntity>.toNotes(): List<Note> = map { it.toNote() }
    fun Note.toEntity(): NoteEntity = NoteEntity(id, title, noteState)
    fun List<Note>.toEntities(): List<NoteEntity> = map { it.toEntity() }
}