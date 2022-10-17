package com.example.notesultimate.ui.util

import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.domain.util.NoteOrder
import com.example.notesultimate.domain.util.OrderType

data class NotesState(
    val notes:List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible:Boolean = false
)
