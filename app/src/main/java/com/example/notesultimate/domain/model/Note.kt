package com.example.notesultimate.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    var title:String,
    var content:String,
    var color:Int,
    var created:Long = System.currentTimeMillis()
)