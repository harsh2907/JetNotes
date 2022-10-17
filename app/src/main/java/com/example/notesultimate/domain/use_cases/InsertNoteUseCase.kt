package com.example.notesultimate.domain.use_cases

import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.domain.repository.NotesRepository

class InsertNoteUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note:Note){
        repository.insertNote(note)
    }
}