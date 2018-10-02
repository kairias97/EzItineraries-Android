package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.ItinerariesRepository
import com.kairias97.ezitineraries.data.OperationWithNoResultCallback
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.data.TripPlansRepository
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.TripPlanHeader
import com.kairias97.ezitineraries.presenter.ITripPlansAdminPresenter
import com.kairias97.ezitineraries.view.ITripPlansAdminView
import javax.inject.Inject

class TripPlansAdminPresenter @Inject constructor(private val mTripPlansRepository: TripPlansRepository,
                                                  private val mItinerariesRepository: ItinerariesRepository)
    : ITripPlansAdminPresenter {
    private var mTripPlansAdminView: ITripPlansAdminView? = null
    override fun onTripPlanVisualizationRequest(tripPlanHeader: TripPlanHeader) {
        mTripPlansAdminView?.navigateToViewTripPlanDestinationScreen(tripPlanHeader.tripPlanId!!)
    }

    override fun onNewTripPlanRequest() {
        mTripPlansAdminView?.navigateToNewTripPlanDestinationScreen()
    }

    override fun onTripPlanRemovalRequest(tripPlanHeader: TripPlanHeader) {
        mTripPlansRepository.deleteTripPlanById(tripPlanHeader.tripPlanId!!,
                object: OperationWithNoResultCallback {
                    override fun onSuccess() {
                        mTripPlansAdminView?.removeTripPlanHeaderFromUI(tripPlanHeader)
                    }

                    override fun onError() {
                        mTripPlansAdminView?.showCommunicationErrorMessage()
                    }

                })

    }

    override fun onItineraryProposalGenerationRequest(tripPlanHeader: TripPlanHeader) {
        mTripPlansAdminView?.showItineraryRequestProgressMessage()
        mItinerariesRepository.generateItineraryProposal(tripPlanHeader.tripPlanId!!,
                object : OperationWithResultCallback<CalculatedItinerary> {
                    override fun onSuccess(result: CalculatedItinerary) {
                        mTripPlansAdminView?.hideItineraryRequestProgressMessage()
                        mTripPlansAdminView?.navigateToViewCalculatedItineraryScreen(result)

                    }

                    override fun onError() {
                        mTripPlansAdminView?.hideItineraryRequestProgressMessage()
                        mTripPlansAdminView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun provideTripPlanHeaders() {
        mTripPlansRepository.getTripPlansHeaders(object: OperationWithResultCallback<List<TripPlanHeader>> {
            override fun onSuccess(result: List<TripPlanHeader>) {
                mTripPlansAdminView?.showTripPlanHeaders(result)
            }

            override fun onError() {
                mTripPlansAdminView?.showCommunicationErrorMessage()
            }

        })
    }

    override fun takeView(view: ITripPlansAdminView) {
        mTripPlansAdminView = view
    }

    override fun dropView() {
        mTripPlansAdminView = null
    }
}