package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.presenter.IItineraryDetailPresenter
import com.kairias97.ezitineraries.presenter.implementations.ItineraryDetailPresenter
import com.kairias97.ezitineraries.ui.activities.ItineraryDetailActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class ItineraryDetailModule {
    @ActivityScoped
    @Binds
    abstract fun itineraryDetailPresenter(presenter: ItineraryDetailPresenter)
            : IItineraryDetailPresenter

    @Module
    companion object {
        @ActivityScoped
        @JvmStatic
        @Provides
        fun provideCalculatedItinerary(activity : ItineraryDetailActivity) : CalculatedItinerary? {
            return if(activity.intent.hasExtra(activity.EXTRA_CALCULATED_ITINERARY)) {
                activity.intent.extras.getParcelable(activity.EXTRA_CALCULATED_ITINERARY)
                        as CalculatedItinerary?
            } else {
                null
            }
        }

        @ActivityScoped
        @JvmStatic
        @Named("calculatedItineraryId")
        @Provides
        fun provideCalculatedItineraryId(activity : ItineraryDetailActivity) : Int? {
            return if (activity.intent.hasExtra(activity.EXTRA_CALCULATED_ITINERARY_ID)) {
                activity.intent.extras.getInt(activity.EXTRA_CALCULATED_ITINERARY_ID)
            } else {
                null
            }
        }
    }
}