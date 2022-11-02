package com.kode.remindme.Notification

import com.kode.remindme.UI.MainActivity
import com.kode.remindme.Room.AppModule
import com.kode.remindme.Room.RoomModule
import com.kode.remindme.map.ForegroundService
import com.kode.remindme.map.ServiceModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NotificationModule::class, ServiceModule::class, RoomModule::class])
interface NotificationComponent {

    fun injectservice(service: ForegroundService)

    fun injectMain(mainActivity: MainActivity)


}

