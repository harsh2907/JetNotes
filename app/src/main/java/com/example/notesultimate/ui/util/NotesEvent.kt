package com.example.notesultimate.ui.util

import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder):NotesEvent()
    data class InsertNote(val note:Note):NotesEvent()
    data class DeleteNote(val note:Note):NotesEvent()
    data class UpdateNote(val note:Note):NotesEvent()
    object RestoreNote:NotesEvent()
    object ToggleOrderSection:NotesEvent()
}