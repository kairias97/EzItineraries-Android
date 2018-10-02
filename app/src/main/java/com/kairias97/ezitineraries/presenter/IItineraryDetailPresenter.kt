package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.view.IItineraryDetailView

interface IItineraryDetailPresenter : BasePresenter<IItineraryDetailView> {
    fun provideItinerary()
    fun onItineraryPreferencesVisualizationRequest()
}