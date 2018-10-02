package com.kairias97.ezitineraries

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.kairias97.ezitineraries.di.DaggerAppComponent
import dagger.android.AndroidInjector
import javax.inject.Inject
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric


class EzItinerariesApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(FlowConfig.Builder(this).build())
        Fabric.with(this, Crashlytics())
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
        //return AndroidInjector {  }
        //return DaggerAppComponent.builder().application(this).build()
    }
}