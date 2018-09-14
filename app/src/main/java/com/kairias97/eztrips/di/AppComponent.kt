package com.kairias97.eztrips.di

import android.app.Application
import com.kairias97.eztrips.EzTripsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [BuildConfigModule::class,
    NetworkModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<EzTripsApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : AppComponent.Builder
        fun build() : AppComponent
    }
}