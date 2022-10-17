package com.example.notesultimate.domain.use_cases

import androidx.compose.ui.text.toLowerCase
import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.domain.repository.NotesRepository
import com.example.notesultimate.domain.util.NoteOrder
import com.example.notesultimate.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.*

class GetNotesUseCase(
    private val repository: NotesRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ):Flow<List<Note>> {
        return repository.fetchNotes().map { notes->
            when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Date -> notes.sortedBy { it.created }
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase(Locale.ROOT) }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Date -> notes.sortedByDescending { it.created }
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase(Locale.ROOT) }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}