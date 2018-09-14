package com.kairias97.eztrips

import android.app.Application
import com.kairias97.eztrips.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager



class EzTripsApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(FlowConfig.Builder(this).build())

    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
        //return AndroidInjector {  }
        //return DaggerAppComponent.builder().application(this).build()
    }
}