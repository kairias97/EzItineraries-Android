package com.kairias97.eztrips.di

import com.kairias97.eztrips.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class BuildConfigModule {

    @Singleton
    @Provides
    @Named("isDebug")
    fun provideIsDebugMode() : Boolean {

        return BuildConfig.DEBUG
    }

    @Singleton
    @Provides
    @Named("itinerariesAPIHost")
    fun provideItinerariesAPIHost() : String {
        return BuildConfig.HOST_ITINERARIES_API
    }

}