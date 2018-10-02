package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanSavedPreferencesPresenter
import com.kairias97.ezitineraries.presenter.implementations.TripPlanSavedPreferencesPresenter
import com.kairias97.ezitineraries.ui.activities.TripPlanAttractionsActivity
import com.kairias97.ezitineraries.ui.activities.TripPlanSavedPreferencesActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class TripPlanSavedPreferencesModule {

    @ActivityScoped
    @Binds
    abstract  fun tripPlanSavedPrererencesPresenter(presenter: TripPlanSavedPreferencesPresenter)
        : ITripPlanSavedPreferencesPresenter

    @Module
    companion object {
        @ActivityScoped
        @JvmStatic
        @Provides
        fun provideTripPlan(activity : TripPlanSavedPreferencesActivity) : TripPlan? {
            return activity.intent.extras.getParcelable(activity.EXTRA_TRIP_PLAN)
                    as TripPlan?
        }

    }

}