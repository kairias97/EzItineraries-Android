package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.view.IItineraryPreferencesView

interface IItineraryPreferencesPresenter : BasePresenter<IItineraryPreferencesView>{
    fun providePreferences()
    fun onItineraryAttractionsVisualizationRequest()
}