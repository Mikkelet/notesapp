package dk.example.notesapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dk.example.notesapp.data.local.NotesDatabase

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    fun bindNoteDatabase(@ApplicationContext context: Context): NotesDatabase =
        Room.databaseBuilder(context, NotesDatabase::class.java, "notes-db")
            .fallbackToDestructiveMigration().build()
}