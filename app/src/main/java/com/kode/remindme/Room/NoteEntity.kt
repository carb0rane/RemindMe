package com.kode.remindme.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val heading:String,
    val mainContent:String,
    val location:String
)
