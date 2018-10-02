package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.model.TripPlanHeader
import com.kairias97.ezitineraries.view.ITripPlansAdminView

interface ITripPlansAdminPresenter : BasePresenter<ITripPlansAdminView> {
    fun onTripPlanVisualizationRequest(tripPlanHeader: TripPlanHeader)
    fun onNewTripPlanRequest()
    fun onTripPlanRemovalRequest(tripPlanHeader: TripPlanHeader)
    fun onItineraryProposalGenerationRequest(tripPlanHeader: TripPlanHeader)
    fun provideTripPlanHeaders()

}