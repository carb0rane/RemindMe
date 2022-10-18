package com.kode.remindme.Room

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

private lateinit var mApplication: Application

constructor(application: Application){
    mApplication = application
}

    @Provides
    @Singleton
    fun provideApplication():Application{
        return mApplication
    }

}
