package com.kode.remindme.Room

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepo {
    private lateinit var noteDAO: NoteDAO

    @Inject
    constructor(noteDAO: NoteDAO){
        this.noteDAO = noteDAO
    }

    suspend fun insertNote(note:NoteEntity){
        noteDAO.addNote(note)
    }

    suspend fun getAllNotes() : Flow<MutableList<NoteEntity>>{
        return noteDAO.getAllNotes()
    }
    suspend fun getNoti(latLng: String):NoteEntity{
        return noteDAO.getNoteFromLatLng(latLng)
    }

    suspend fun deleteNotes(latLng: String){
        noteDAO.deleteNote(latLng)
    }
}