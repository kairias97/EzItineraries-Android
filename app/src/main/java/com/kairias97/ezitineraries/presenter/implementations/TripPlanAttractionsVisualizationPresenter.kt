package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.OperationWithNoResultCallback
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.data.TouristAttractionsRepository
import com.kairias97.ezitineraries.data.TripPlansRepository
import com.kairias97.ezitineraries.model.TouristAttraction
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanAttractionsVisualizationPresenter
import com.kairias97.ezitineraries.view.ITripPlanAttractionsVisualizationView
import javax.inject.Inject
import javax.inject.Named

class TripPlanAttractionsVisualizationPresenter @Inject constructor(private val mTripPlan: TripPlan?,
                                                                    @Named("isNewTripPlan")private val mIsNewTripPlan: Boolean,
                                                                    private val mTripPlansRepository: TripPlansRepository,
                                                                    private val mTouristAttractionsRepository
                                                                    : TouristAttractionsRepository)
    : ITripPlanAttractionsVisualizationPresenter {

    private var mITripPlanAttractionsVisualizationView : ITripPlanAttractionsVisualizationView? = null

    override fun provideTouristAttractions() {
        mTouristAttractionsRepository.getAttractionsByCity(mTripPlan!!.city!!.cityId!!,
                object: OperationWithResultCallback<List<TouristAttraction>> {
                    override fun onSuccess(result: List<TouristAttraction>) {
                        mITripPlanAttractionsVisualizationView?.showTouristAttractionsUI(result)
                    }

                    override fun onError() {
                        mITripPlanAttractionsVisualizationView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun onTripPlanSaveConfirmation() {
        mITripPlanAttractionsVisualizationView?.showTripPlanSavingProgressMessage()
        mTripPlansRepository.saveTripPlan(mTripPlan!!,
                object: OperationWithNoResultCallback{
                    override fun onSuccess() {
                        mITripPlanAttractionsVisualizationView?.hideTripPlanSavingProgressMessage()
                        mITripPlanAttractionsVisualizationView?.showSavedTripPlanSuccessMessage()
                        mITripPlanAttractionsVisualizationView?.navigateToMainScreen()
                    }

                    override fun onError() {
                        mITripPlanAttractionsVisualizationView?.hideTripPlanSavingProgressMessage()
                        mITripPlanAttractionsVisualizationView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun takeView(view: ITripPlanAttractionsVisualizationView) {
        mITripPlanAttractionsVisualizationView = view
        if (mIsNewTripPlan) {
            mITripPlanAttractionsVisualizationView?.showSaveTripPlanButton()
        }

    }

    override fun dropView() {
        mITripPlanAttractionsVisualizationView = null
    }

}