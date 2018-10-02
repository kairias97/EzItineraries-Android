package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanDetailPresenter
import com.kairias97.ezitineraries.presenter.implementations.TripPlanDetailPresenter
import com.kairias97.ezitineraries.ui.activities.TripPlanAttractionsActivity
import com.kairias97.ezitineraries.ui.activities.TripPlanDetailActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class TripPlanDetailModule {

    @ActivityScoped
    @Binds
    abstract fun tripPlanDetailPresenter(presenter: TripPlanDetailPresenter)
            : ITripPlanDetailPresenter
    @Module
    companion object {

        @ActivityScoped
        @JvmStatic
        @Named("tripPlanId")
        @Provides
        fun provideTripPlanId(activity : TripPlanDetailActivity) : Int {
            return activity.intent.extras.getInt(activity.EXTRA_TRIP_PLAN_ID)
        }
    }
}