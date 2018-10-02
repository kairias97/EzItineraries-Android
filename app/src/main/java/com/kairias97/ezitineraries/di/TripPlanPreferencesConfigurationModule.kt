package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanPreferencesConfigurationPresenter
import com.kairias97.ezitineraries.presenter.implementations.TripPlanPreferencesConfigurationPresenter
import com.kairias97.ezitineraries.ui.activities.TripPlanPreferencesConfigurationActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class TripPlanPreferencesConfigurationModule {

    @ActivityScoped
    @Provides
    fun configureTripPlanPreferencesConfigurationPresenter(presenter: TripPlanPreferencesConfigurationPresenter)
            : ITripPlanPreferencesConfigurationPresenter {
        return presenter
    }

    @ActivityScoped
    @Provides
    fun provideTripPlan(activity : TripPlanPreferencesConfigurationActivity) : TripPlan? {
        return activity.intent.extras.getParcelable(activity.EXTRA_TRIP_PLAN)
                as TripPlan?
    }

}