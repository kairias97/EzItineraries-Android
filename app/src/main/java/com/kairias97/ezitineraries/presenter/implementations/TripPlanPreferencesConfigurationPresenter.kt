package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.data.TouristAttractionsRepository
import com.kairias97.ezitineraries.model.Category
import com.kairias97.ezitineraries.model.TouristTripPreference
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.ITripPlanPreferencesConfigurationPresenter
import com.kairias97.ezitineraries.view.ITripPlanPreferencesConfigurationView
import javax.inject.Inject

class TripPlanPreferencesConfigurationPresenter @Inject constructor(private val mTouristAttractionsRepository
                                                                    : TouristAttractionsRepository,
                                                                    private val mTripPlan: TripPlan?)
    : ITripPlanPreferencesConfigurationPresenter{

    private var mTripPlanPreferencesConfigurationView : ITripPlanPreferencesConfigurationView? = null

    override fun provideCategoryPreferences() {
        mTripPlanPreferencesConfigurationView?.showCategoriesLoadProgressMessage()
        mTouristAttractionsRepository.getCategories(object:OperationWithResultCallback<List<Category>> {
            override fun onSuccess(result: List<Category>) {
                mTripPlanPreferencesConfigurationView?.hideCategoriesLoadProgressMessage()
                var preferences = result.map {
                    var tripPreference = TouristTripPreference()
                    tripPreference.category = it
                    tripPreference.assignedWeight = 0.00
                    tripPreference
                }
                mTripPlanPreferencesConfigurationView?.showCategoryPreferencesListUI(preferences)
            }

            override fun onError() {
                mTripPlanPreferencesConfigurationView?.hideCategoriesLoadProgressMessage()
                mTripPlanPreferencesConfigurationView?.showCommunicationErrorMessage()
            }

        })
    }

    override fun onNextStepRequest(preferences: List<TouristTripPreference>) {
        var totalWeight = 0.00
        for (p in preferences) {
            totalWeight += (p.assignedWeight!! * 100.00)
        }
        if (totalWeight == 100.00) {
            this.mTripPlan!!.preferences = preferences
            mTripPlanPreferencesConfigurationView?.navigateToLastStep(mTripPlan!!)
        } else {
            mTripPlanPreferencesConfigurationView?.showInvalidWeightsMessage()

        }

    }

    override fun takeView(view: ITripPlanPreferencesConfigurationView) {
        mTripPlanPreferencesConfigurationView = view
    }

    override fun dropView() {
        mTripPlanPreferencesConfigurationView = null
    }
}