package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanSavedPreferencesPresenter
import com.kairias97.ezitineraries.view.ITripPlanSavedPreferencesView
import javax.inject.Inject

class TripPlanSavedPreferencesPresenter @Inject constructor(private val mTripPlan: TripPlan?)
    : ITripPlanSavedPreferencesPresenter {

    private var mTripPlanSavedPreferencesView: ITripPlanSavedPreferencesView? = null
    override fun providePreferences() {
        mTripPlanSavedPreferencesView?.showTripPlanPreferencesUI(mTripPlan!!.preferences!!)
    }

    override fun onAttractionsVisualizationRequest() {
        mTripPlanSavedPreferencesView?.navigateToTripPlanVisualization(mTripPlan!!)
    }

    override fun takeView(view: ITripPlanSavedPreferencesView) {
        mTripPlanSavedPreferencesView = view
    }

    override fun dropView() {
        mTripPlanSavedPreferencesView = null
    }
}