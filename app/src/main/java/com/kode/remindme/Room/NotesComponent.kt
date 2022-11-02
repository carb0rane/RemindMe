package com.kode.remindme.Room

import android.app.Application
import com.kode.remindme.UI.AddNoteActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RoomModule::class])
@Singleton
interface NotesComponent {

     fun inject(noteActivity: AddNoteActivity)


     fun application():Application

}