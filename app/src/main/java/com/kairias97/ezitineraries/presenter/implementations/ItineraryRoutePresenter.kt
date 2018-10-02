package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.ItinerariesRepository
import com.kairias97.ezitineraries.data.OperationWithNoResultCallback
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.data.TouristAttractionsRepository
import com.kairias97.ezitineraries.model.CalculatedItinerary
import com.kairias97.ezitineraries.model.TouristAttraction
import com.kairias97.ezitineraries.presenter.IItineraryRoutePresenter
import com.kairias97.ezitineraries.view.IItineraryRouteView
import javax.inject.Inject
import javax.inject.Named

class ItineraryRoutePresenter @Inject constructor(private val mCalculatedItinerary: CalculatedItinerary?,
                                                  @Named("isNewItinerary")private val mIsNewItinerary: Boolean,
                                                  private val mItinerariesRepository : ItinerariesRepository,
                                                  private val mTouristAttractionsRepository : TouristAttractionsRepository) : IItineraryRoutePresenter {

    private var mItineraryRouteView : IItineraryRouteView? = null

    override fun provideTouristVisits() {
        mItineraryRouteView?.traceVisitRoutes(mCalculatedItinerary!!.touristVisits!!)
        mTouristAttractionsRepository.getAttractionsByCity(mCalculatedItinerary!!.city!!.cityId!!,
                object: OperationWithResultCallback<List<TouristAttraction>> {
                    override fun onSuccess(result: List<TouristAttraction>) {
                        var attractionsToShow = result.filter {
                            val currentAttraction = it
                            !mCalculatedItinerary!!.touristVisits!!.any { it.touristAttraction!!.id == currentAttraction!!.id }
                        }
                        mItineraryRouteView?.showTouristAttractions(attractionsToShow)
                    }

                    override fun onError() {
                        mItineraryRouteView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun onItinerarySaveRequest() {
        mItineraryRouteView?.showItinerarySavingProgressMessage()
        mItinerariesRepository.saveItinerary(mCalculatedItinerary!!,
                object: OperationWithNoResultCallback {
                    override fun onSuccess() {

                        mItineraryRouteView?.hideItinerarySavingProgressMessage()
                        mItineraryRouteView?.showSavedItineraryMessage()
                        mItineraryRouteView?.navigateToMainScreen()
                    }

                    override fun onError() {

                        mItineraryRouteView?.hideItinerarySavingProgressMessage()
                        mItineraryRouteView?.showCommunicationErrorMessage()
                    }


                })
    }

    override fun takeView(view: IItineraryRouteView) {
        mItineraryRouteView = view
        if (mIsNewItinerary) {
            mItineraryRouteView?.showItinerarySaveButton()
        }
    }

    override fun dropView() {
        mItineraryRouteView = null
    }
}