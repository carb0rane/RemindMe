package com.kode.remindme.UI

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.kode.remindme.Notification.DaggerNotificationComponent
import com.kode.remindme.Room.*
import com.kode.remindme.databinding.ActivityMainBinding
import com.kode.remindme.map.Data
import com.kode.remindme.map.ForegroundService
import com.kode.remindme.map.GeofenceBroadcastReceiver
import com.kode.remindme.Notification.NotificationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var geofenceBroadcastReceiver: GeofenceBroadcastReceiver

    @Inject
    lateinit var notesrepo: NotesRepo

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        DaggerNotificationComponent.builder().appModule(AppModule(application)).roomModule(
            RoomModule(application)
        ).notificationModule(
            NotificationModule(application)
        ).build()
            .injectMain(this)

        getpermissions()
        sendCommandToService(Data.ACTION_START_SERVICE)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.notesRepo = notesrepo
        mainViewModel.geofencingClient = LocationServices.getGeofencingClient(this)



        CoroutineScope(Dispatchers.IO).launch {
            mainViewModel.getNotes()
            withContext(Main) {
                showrcView()
            }
        }

        binding.btnCreateNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }


    }

    private fun showrcView() {
        binding.rcViewNotes.adapter = HomeRecyclerView(mainViewModel)
        binding.rcViewNotes.layoutManager = LinearLayoutManager(this)
    }

    private fun sendCommandToService(action: String) {
        startService(
            Intent(this, ForegroundService::class.java).apply {
                this.action = action
            }
        )
    }

    fun getpermissions() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // User Granted the Permission
                } else {
                    Log.d(TAG, "getpermissions: Permission not granted !")
                }
            }
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED -> {
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED -> {
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
            else -> {
                // All permission given !

            }
        }
    }


}