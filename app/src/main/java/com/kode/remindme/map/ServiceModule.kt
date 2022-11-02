package com.kode.remindme.map

import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    lateinit var service: ForegroundService

    constructor(service: ForegroundService) {
        this.service = service
    }

    @Provides
    fun provideService() : ForegroundService{
        return service
    }
}