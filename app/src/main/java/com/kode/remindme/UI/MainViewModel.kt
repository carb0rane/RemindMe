package com.kode.remindme.UI

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.GeofencingClient
import com.kode.remindme.Room.NoteEntity
import com.kode.remindme.Room.NotesRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainViewModel"

class MainViewModel(application: Application) : AndroidViewModel(application) {


    var allNotes: MutableList<NoteEntity> = arrayListOf()
    lateinit var notesRepo: NotesRepo
    lateinit var geofencingClient: GeofencingClient


    fun getNotes() {
        allNotes.clear()
        CoroutineScope(Dispatchers.IO).launch {
            try {


                notesRepo.getAllNotes().collect {
                    allNotes = it
                }
            } catch (e: Exception) {
                Log.d(TAG, "getNotes: $e")
            }
        }

    }

    fun deleteNote(location: String, markerID: String) {

        CoroutineScope(Dispatchers.IO).launch {

            notesRepo.deleteNotes(location)
            withContext(Main){
                geofencingClient.removeGeofences(arrayListOf(markerID)).run {
                    addOnSuccessListener {
                        Log.d(TAG, "deleteNote: Success ! ")
                    }
                    addOnFailureListener {
                        Log.d(TAG, "Failed to delete geofence : $it ")
                    }
                }
            }

        }
    }

}
