package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.ItinerariesRepository
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.presenter.IItineraryDetailPresenter
import com.kairias97.ezitineraries.view.IItineraryDetailView
import javax.inject.Inject
import javax.inject.Named

class ItineraryDetailPresenter @Inject constructor(private var mCalculatedItinerary: CalculatedItinerary?,
                                                   @Named("calculatedItineraryId") private val mCalculatedItineraryId : Int?,
                                                   private val mItineraryRepository: ItinerariesRepository)
    : IItineraryDetailPresenter {
    private var mItineraryDetailView : IItineraryDetailView? = null
    override fun provideItinerary() {
        if (mCalculatedItinerary != null) {
            mItineraryDetailView?.showItineraryInformation(mCalculatedItinerary!!)
        } else {
            mItineraryRepository.getItineraryInfoById(mCalculatedItineraryId!!,
                    object : OperationWithResultCallback<CalculatedItinerary> {
                        override fun onSuccess(result: CalculatedItinerary) {
                            mCalculatedItinerary = result
                            mItineraryDetailView?.showItineraryInformation(result)
                        }

                        override fun onError() {
                            mItineraryDetailView?.showCommunicationErrorMessage()
                        }

                    })
        }
    }

    override fun onItineraryPreferencesVisualizationRequest() {
        if (mCalculatedItinerary != null) {
            //It is new when an itinerary id was not passed to the presenter
            var isNewItinerary = mCalculatedItinerary!!.id == 0
            mItineraryDetailView?.navigateToItineraryPreferencesVisualization(mCalculatedItinerary!!,
                    isNewItinerary)
        }

    }

    override fun takeView(view: IItineraryDetailView) {
        mItineraryDetailView = view
    }

    override fun dropView() {
        mItineraryDetailView = null
    }

}