package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.presenter.BasePresenter
import com.kairias97.ezitineraries.view.ITripPlanAttractionsVisualizationView

interface ITripPlanAttractionsVisualizationPresenter
    : BasePresenter<ITripPlanAttractionsVisualizationView> {
    fun provideTouristAttractions()
    fun onTripPlanSaveConfirmation()
}