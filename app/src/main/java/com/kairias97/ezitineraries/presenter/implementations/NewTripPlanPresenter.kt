package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.data.TouristAttractionsRepository
import com.kairias97.ezitineraries.data.TouristDestinationsRepository
import com.kairias97.ezitineraries.model.City
import com.kairias97.ezitineraries.model.Country
import com.kairias97.ezitineraries.model.ItineraryRestriction
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.INewTripPlanPresenter
import com.kairias97.ezitineraries.view.INewTripPlanView
import java.util.*
import javax.inject.Inject

class NewTripPlanPresenter @Inject constructor(private val mTouristDestinationsRepository
                                               : TouristDestinationsRepository)
    : INewTripPlanPresenter {

    private var mNewTripPlanView: INewTripPlanView? = null
    override fun provideCountries() {
        mTouristDestinationsRepository.getCountries(true,
                object: OperationWithResultCallback<List<Country>> {
                    override fun onSuccess(result: List<Country>) {
                        mNewTripPlanView?.showCountriesListUI(result)

                    }

                    override fun onError() {

                        mNewTripPlanView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun onSelectedCountry(country: Country) {
        mTouristDestinationsRepository.getCities(true, country.isoNumericCode!!,
                object:OperationWithResultCallback<List<City>> {
                    override fun onSuccess(result: List<City>) {
                        mNewTripPlanView?.showCitiesListUI(result)
                    }

                    override fun onError() {
                        mNewTripPlanView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun onNextStepRequest(city: City, tripDate: Date?, tripName: String, tripDescription: String, maxDistance: Double?) {
        var tripPlan =  TripPlan()
        tripPlan.city = city
        tripPlan.date = tripDate
        tripPlan.name = tripName
        tripPlan.description = tripDescription
        tripPlan.restriction = ItineraryRestriction(maxDistance)

        mNewTripPlanView?.navigateToConfigurePreferences(tripPlan)
    }

    override fun validateData(date: String, name: String, distance: String): Boolean {
        return date.isNotEmpty() && name.isNotEmpty() && distance.isNotEmpty()
    }

    override fun takeView(view: INewTripPlanView) {
        mNewTripPlanView = view
    }

    override fun dropView() {
        mNewTripPlanView = null
    }
}