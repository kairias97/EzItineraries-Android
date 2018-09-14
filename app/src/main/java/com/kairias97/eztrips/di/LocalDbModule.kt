package com.kairias97.eztrips.di

import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.Database
import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class LocalDbModule {

    @Singleton
    @Provides
    fun providesLocalDatabase() : DatabaseDefinition {
        return FlowManager.getDatabase(AppDatabase::class.java)
    }

}