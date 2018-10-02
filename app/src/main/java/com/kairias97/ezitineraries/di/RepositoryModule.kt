package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.data.*
import com.kairias97.ezitineraries.data.local.CalculatedItineraryDAO
import com.kairias97.ezitineraries.data.local.TouristAttractionDAO
import com.kairias97.ezitineraries.data.local.TripPlanDAO
import com.kairias97.ezitineraries.data.remote.ConnectivityChecker
import com.kairias97.ezitineraries.data.remote.ItinerariesService
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun providesItineraryRepository(centralRepository: CentralRepository)
            : ItinerariesRepository
    @Binds
    abstract fun providesTouristDestinationRepository(centralRepository: CentralRepository)
            : TouristDestinationsRepository
    @Binds
    abstract fun providesTouristAttractionRepository(centralRepository: CentralRepository)
            : TouristAttractionsRepository
    @Binds
    abstract fun providesTouristSuggestionRepository(centralRepository: CentralRepository)
            : TouristSuggestionsRepository
    @Binds
    abstract fun providesTripPlanRepository(centralRepository: CentralRepository)
            : TripPlansRepository

}