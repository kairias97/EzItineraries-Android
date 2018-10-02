package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanAttractionsVisualizationPresenter
import com.kairias97.ezitineraries.presenter.implementations.TripPlanAttractionsVisualizationPresenter
import com.kairias97.ezitineraries.ui.activities.TripPlanAttractionsActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class TripPlanAttractionsVisualizationModule {
    @ActivityScoped
    @Binds
    abstract fun tripPlanAttractionsPresenter(presenter: TripPlanAttractionsVisualizationPresenter)
        : ITripPlanAttractionsVisualizationPresenter

    @Module
    companion object {
        @ActivityScoped
        @JvmStatic
        @Provides
        fun provideTripPlan(activity : TripPlanAttractionsActivity) : TripPlan? {
            return activity.intent.extras.getParcelable(activity.EXTRA_TRIP_PLAN)
                    as TripPlan?
        }

        @ActivityScoped
        @JvmStatic
        @Named("isNewTripPlan")
        @Provides
        fun provideIsNewTripPlan(activity : TripPlanAttractionsActivity) : Boolean {
            return activity.intent.extras.getBoolean(activity.EXTRA_IS_NEW_TRIP_PLAN)
        }
    }
}