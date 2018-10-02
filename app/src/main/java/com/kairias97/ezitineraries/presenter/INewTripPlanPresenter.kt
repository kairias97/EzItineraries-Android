package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.model.City
import com.kairias97.ezitineraries.model.Country
import com.kairias97.ezitineraries.view.INewTripPlanView
import java.util.*

interface INewTripPlanPresenter : BasePresenter<INewTripPlanView>{
    fun provideCountries()
    fun onSelectedCountry(country: Country)
    fun onNextStepRequest(city: City, tripDate: Date?,
                          tripName: String, tripDescription: String,
                          maxDistance: Double?)
    fun validateData(date: String, name: String, distance: String): Boolean
}