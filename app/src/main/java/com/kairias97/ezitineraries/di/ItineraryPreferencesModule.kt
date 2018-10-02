package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.presenter.IItineraryPreferencesPresenter
import com.kairias97.ezitineraries.presenter.implementations.ItineraryPreferencesPresenter
import com.kairias97.ezitineraries.ui.activities.ItineraryDetailActivity
import com.kairias97.ezitineraries.ui.activities.ItineraryPreferencesActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class ItineraryPreferencesModule {

    @ActivityScoped
    @Binds
    abstract fun itineraryPreferencesPresenter(presenter: ItineraryPreferencesPresenter)
        : IItineraryPreferencesPresenter

    @Module
    companion object {
        @ActivityScoped
        @JvmStatic
        @Provides
        fun provideCalculatedItinerary(activity : ItineraryPreferencesActivity) : CalculatedItinerary? {
            return activity.intent.extras.getParcelable(activity.EXTRA_CALCULATED_ITINERARY)
                    as CalculatedItinerary?
        }

        @ActivityScoped
        @JvmStatic
        @Named("isNewItinerary")
        @Provides
        fun provideIsNewItinerary(activity : ItineraryPreferencesActivity) : Boolean {
            return activity.intent.extras.getBoolean(activity.EXTRA_IS_NEW_ITINERARY)
        }
    }
}