package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.ItinerariesRepository
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.presenter.IItineraryPreferencesPresenter
import com.kairias97.ezitineraries.view.IItineraryDetailView
import com.kairias97.ezitineraries.view.IItineraryPreferencesView
import javax.inject.Inject
import javax.inject.Named


class ItineraryPreferencesPresenter @Inject constructor(private val mCalculatedItinerary: CalculatedItinerary?,
                                                        @Named("isNewItinerary") private val mIsNew : Boolean,
                                                        private val mItineraryRepository: ItinerariesRepository)
    : IItineraryPreferencesPresenter {

    private var mItineraryPreferencesView : IItineraryPreferencesView? = null


    override fun providePreferences() {
        mItineraryPreferencesView?.showPreferencesUI(mCalculatedItinerary!!.itineraryPreferences!!)
    }

    override fun onItineraryAttractionsVisualizationRequest() {
        mItineraryPreferencesView?.navigateToItineraryAttractionsVisualization(mCalculatedItinerary!!
                , mIsNew)
    }

    override fun takeView(view: IItineraryPreferencesView) {
        mItineraryPreferencesView = view
    }

    override fun dropView() {
        mItineraryPreferencesView = null
    }

}