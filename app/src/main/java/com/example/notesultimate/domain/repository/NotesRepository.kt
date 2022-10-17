package com.example.notesultimate.domain.repository

import com.example.notesultimate.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun fetchNotes():Flow<List<Note>>

    suspend fun insertNote(note:Note)

    suspend fun deleteNote(note:Note)

    suspend fun getNoteById(id:Int):Note?

    suspend fun updateNote(note: Note)

}