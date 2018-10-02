package com.kairias97.ezitineraries.di

import android.app.Application
import com.kairias97.ezitineraries.EzItinerariesApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [BuildConfigModule::class,
    NetworkModule::class,
    LocalDbModule::class,
    RepositoryModule::class,
    ApplicationModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<EzItinerariesApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : AppComponent.Builder
        fun build() : AppComponent
    }
}