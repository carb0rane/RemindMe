package com.kode.remindme.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: NoteEntity)

     @Query("SELECT * from notes_table")
     fun getAllNotes():List<NoteEntity>

}