package com.kode.remindme.Room

import androidx.lifecycle.LiveData
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

    suspend fun getAllNotes() : List<NoteEntity>{
        return noteDAO.getAllNotes()
    }

}