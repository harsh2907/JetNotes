package com.example.notesultimate.ui.screens.viewmodel

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach { notes ->
            _state.value = state.value.copy(notes = notes, noteOrder = noteOrder)
        }.launchIn(viewModelScope)
    }

}