package com.kode.remindme

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kode.remindme.Room.*
import com.kode.remindme.databinding.ActivityAddNoteBinding
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

DaggerNotesComponent.builder().appModule(AppModule(application)).roomModule(RoomModule(application)).build().inject(this)

        CoroutineScope(Dispatchers.IO).launch {
            notesRepo.insertNote((NoteEntity(55,"Heading","test","patna")))
            delay(1000)
            Log.d(TAG, "onCreate: ${notesRepo.getAllNotes()}")
        }



    }


}