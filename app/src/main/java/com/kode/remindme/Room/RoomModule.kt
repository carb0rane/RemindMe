package com.kode.remindme.Room

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    private lateinit var notesDatabase: NotesDatabase


    constructor(mApplication: Application){

        notesDatabase = NotesDatabase.getNotesDatabaseInstance(mApplication)
    }

    @Singleton
    @Provides
    fun getDatabase():NotesDatabase{
        return notesDatabase
    }

    @Singleton
    @Provides
    fun getDao(database: NotesDatabase): NoteDAO{
        return database.getNotesDao()
    }


    @Singleton
    @Provides
    fun getRepo(noteDAO:NoteDAO): NotesRepo{
       return NotesRepo(noteDAO)
    }




}