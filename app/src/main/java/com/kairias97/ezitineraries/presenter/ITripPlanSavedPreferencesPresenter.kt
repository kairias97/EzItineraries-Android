package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.view.ITripPlanSavedPreferencesView

interface ITripPlanSavedPreferencesPresenter : BasePresenter<ITripPlanSavedPreferencesView> {
    fun providePreferences()
    fun onAttractionsVisualizationRequest()
}