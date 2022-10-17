package com.example.notesultimate.data_source.repository

import com.example.notesultimate.data_source.local.NotesDao
import com.example.notesultimate.data_source.local.NotesDatabase
import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val dao:NotesDao
):NotesRepository {
    override fun fetchNotes() = dao.getNotes()

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun updateNote(note: Note) {
        return dao.updateNote(note)
    }
}