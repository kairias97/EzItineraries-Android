package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.model.TouristTripPreference
import com.kairias97.ezitineraries.view.ITripPlanPreferencesConfigurationView

interface ITripPlanPreferencesConfigurationPresenter
    : BasePresenter<ITripPlanPreferencesConfigurationView>{
    fun provideCategoryPreferences()
    fun onNextStepRequest(preferences: List<TouristTripPreference>)
}