package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.view.ITripPlanDetailView

interface ITripPlanDetailPresenter : BasePresenter<ITripPlanDetailView> {
    fun provideTripPlan()
    fun onPreferencesVisualizationRequest()
}