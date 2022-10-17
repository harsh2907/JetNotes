package com.example.notesultimate.di

import android.content.Context
import androidx.room.Room
import com.example.notesultimate.data_source.local.NotesDatabase
import com.example.notesultimate.data_source.repository.NotesRepositoryImpl
import com.example.notesultimate.domain.repository.NotesRepository
import com.example.notesultimate.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context):NotesDatabase =
        Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "Notesdb"
        ).fallbackToDestructiveMigration()
            .build()

  @Singleton
  @Provides
  fun provideRepository(db:NotesDatabase):NotesRepository = NotesRepositoryImpl(db.notesDao)

    @Singleton
    @Provides
    fun provideUseCase(repository: NotesRepository) =
        NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            insertNote = InsertNoteUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            updateNote = UpdateNoteUseCase(repository)
        )

}