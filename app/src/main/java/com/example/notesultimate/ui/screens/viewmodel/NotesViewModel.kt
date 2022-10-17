package com.example.notesultimate.ui.screens.viewmodel

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.domain.repository.NotesRepository
import com.example.notesultimate.domain.use_cases.NoteUseCases
import com.example.notesultimate.domain.util.NoteOrder
import com.example.notesultimate.domain.util.OrderType
import com.example.notesultimate.ui.util.NotesEvent
import com.example.notesultimate.ui.util.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    private var getNotesJob: Job? = null

    private var recentlyDeleteNote: Note? = null

    private val _note = MutableStateFlow<Note?>(null)
    val note = _note.asStateFlow()

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun setNotes(notes:List<Note>){
        _state.value = state.value.copy(notes = notes)
    }
    fun setNote(note: Note?) {
        _note.value = note
    }


    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.InsertNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    noteUseCases.insertNote(event.note)
                }
            }
            is NotesEvent.UpdateNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    noteUseCases.updateNote(event.note)
                }
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeleteNote = event.note
                }
            }
            is NotesEvent.Order -> {
                if (state.value.noteOrder == event.noteOrder && state.value.noteOrder.orderType == event.noteOrder.orderType) return

                getNotes(event.noteOrder)
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    noteUseCases.insertNote(recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            }
            NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach { notes ->
            _state.value = state.value.copy(notes = notes, noteOrder = noteOrder)
        }.launchIn(viewModelScope)
    }



     fun searchNotes(query: String) {
         val filteredList = buildList {
             state.value.notes.forEach { note->
                 if(note.title
                         .lowercase()
                         .contains(query.lowercase()) || note.content.lowercase()
                         .contains(query.lowercase())
                 ){
                     add(note)
                 }
             }
         }
         setNotes(filteredList)
    }
}