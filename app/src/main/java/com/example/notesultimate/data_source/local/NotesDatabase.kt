package com.example.notesultimate.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesultimate.domain.model.Note

@Database(
    entities = [Note::class],
    version = 2,
    exportSchema = false
)
abstract class NotesDatabase :RoomDatabase(){
    abstract val notesDao:NotesDao
}