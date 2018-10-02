package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.data.local.*
import com.raizlabs.android.dbflow.annotation.Database
import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDbModule {

    @Singleton
    @Provides
    fun providesLocalDatabase() : DatabaseDefinition {
        return FlowManager.getDatabase(AppDatabase::class.java)
    }
    @Singleton
    @Provides
    fun providesCalculatedItineraryDAO(repo: LocalRepository) : CalculatedItineraryDAO {
        return repo
    }
    @Singleton
    @Provides
    fun providesTripPlanDAO(repo: LocalRepository) : TripPlanDAO {
        return repo
    }

    @Singleton
    @Provides
    fun providesTouristAttractionsDAO(repo: LocalRepository) : TouristAttractionDAO {
        return repo
    }

}