package com.kairias97.ezitineraries.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {
    //Expose application as an injectable context
    @Binds
    abstract fun bindContext(application: Application) : Context
}