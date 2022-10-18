package com.kode.remindme.Room

import android.app.Application
import com.kode.remindme.AddNoteActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RoomModule::class])
@Singleton
interface NotesComponent {

     fun inject(noteActivity: AddNoteActivity)

     fun getDatabase():NotesDatabase

     fun getDao():NoteDAO

     fun getNotesRepo():NotesRepo

     fun application():Application


}