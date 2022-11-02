package com.kode.remindme.UI

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.kode.remindme.R
import com.kode.remindme.Room.*
import com.kode.remindme.databinding.ActivityAddNoteBinding
import com.kode.remindme.map.Data
import com.kode.remindme.map.Data.markers
import com.kode.remindme.map.MapsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AddNoteActivity"

class AddNoteActivity : AppCompatActivity() {

    @Inject
    lateinit var notesRepo: NotesRepo

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerNotesComponent.builder().appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build().inject(this)


        binding.btnPickLocation.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frmLayoutMaps,MapsFragment())
                addToBackStack(null)
                commit()
            }
        }

        binding.btnNoteSubmit.setOnClickListener {

            val id = Data.getId()
            val heading = binding.etNoteHeading.text.toString()
            val detail = binding.etNoteMainContent.text.toString()
            val location = markers[0]
            val markerID = Data.markerID
            try {
                addNote(id,heading,detail,location,markerID)
                finish()
            }catch (e:Exception){
                Log.d(TAG, "onCreate : $e")
            }
        }


    }
    private fun addNote(id:Int,heading:String,detail:String,location:LatLng,markerID:String){
        CoroutineScope(Dispatchers.IO).launch {
            notesRepo.insertNote((NoteEntity(id, heading, detail, location.toString(),markerID)))
            delay(1000)
            Log.d(TAG, "addNote: ${notesRepo.getAllNotes()}")
        }
    }


}