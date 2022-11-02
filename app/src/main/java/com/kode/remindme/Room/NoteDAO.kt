package com.kode.remindme.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: NoteEntity)

     @Query("SELECT * from notes_table")
     fun getAllNotes():Flow<MutableList<NoteEntity>>

     @Query("SELECT * from notes_table WHERE location = :location")
     fun getNoteFromLatLng(location : String):NoteEntity

     @Query("DELETE from notes_table where location = :location")
     fun deleteNote(location: String)

}