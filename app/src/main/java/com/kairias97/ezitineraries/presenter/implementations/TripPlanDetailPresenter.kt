package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.data.TripPlansRepository
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanDetailPresenter
import com.kairias97.ezitineraries.view.ITripPlanDetailView
import javax.inject.Inject
import javax.inject.Named

class TripPlanDetailPresenter @Inject constructor(@Named("tripPlanId") private val tripPlanId: Int,
                                                  private val mTripPlansRepository
                                                  : TripPlansRepository)
    : ITripPlanDetailPresenter {

    private var mTripPlanDetailView : ITripPlanDetailView? = null
    private var mTripPlan: TripPlan? = null

    override fun provideTripPlan() {
        mTripPlansRepository.getTripPlanInfoById(tripPlanId,
                object:OperationWithResultCallback<TripPlan> {
                    override fun onSuccess(result: TripPlan) {
                        mTripPlan = result
                        mTripPlanDetailView?.showTripPlanInformation(mTripPlan!!)
                    }

                    override fun onError() {
                        mTripPlanDetailView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun onPreferencesVisualizationRequest() {
        if (mTripPlan != null){
            mTripPlanDetailView?.navigateToTripPlanPreferencesVisualization(mTripPlan!!)
        }
    }

    override fun takeView(view: ITripPlanDetailView) {
        mTripPlanDetailView = view
    }

    override fun dropView() {
        mTripPlanDetailView = null
    }
}