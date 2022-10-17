package com.example.notesultimate.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesultimate.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id:Int):Note?



}