package dk.example.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dk.example.notesapp.data.local.daos.NoteDao
import dk.example.notesapp.data.local.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}

