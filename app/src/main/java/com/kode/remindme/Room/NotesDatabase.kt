package com.kode.remindme.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NoteDAO

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null
        fun getNotesDatabaseInstance(context: Context): NotesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(lock = this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes_table"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}