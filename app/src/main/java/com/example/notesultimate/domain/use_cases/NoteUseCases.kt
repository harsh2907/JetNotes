package com.example.notesultimate.domain.use_cases

data class NoteUseCases(
    val getNotes:GetNotesUseCase,
    val insertNote:InsertNoteUseCase,
    val deleteNote:DeleteNoteUseCase,
    val updateNote:UpdateNoteUseCase
)
