package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.presenter.IItineraryRoutePresenter
import com.kairias97.ezitineraries.presenter.implementations.ItineraryRoutePresenter
import com.kairias97.ezitineraries.ui.activities.ItineraryPreferencesActivity
import com.kairias97.ezitineraries.ui.activities.ItineraryRouteActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class ItineraryRouteModule {

    @ActivityScoped
    @Binds
    abstract fun itineraryRoutePresenter(presenter: ItineraryRoutePresenter)
        : IItineraryRoutePresenter

    @Module
    companion object {
        @ActivityScoped
        @JvmStatic
        @Provides
        fun provideCalculatedItinerary(activity : ItineraryRouteActivity) : CalculatedItinerary? {
            return activity.intent.extras.getParcelable(activity.EXTRA_CALCULATED_ITINERARY)
                    as CalculatedItinerary?
        }

        @ActivityScoped
        @JvmStatic
        @Named("isNewItinerary")
        @Provides
        fun provideIsNewItinerary(activity : ItineraryRouteActivity) : Boolean {
            return activity.intent.extras.getBoolean(activity.EXTRA_IS_NEW_ITINERARY)
        }
    }

}