package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.Category
import com.kairias97.ezitineraries.model.City
import com.kairias97.ezitineraries.model.Country
import com.kairias97.ezitineraries.model.TripPlan

interface INewTripPlanView : IBaseView{
    fun navigateToConfigurePreferences(tripPlan: TripPlan)
    fun showCountriesListUI(countries : List<Country>)
    fun showCitiesListUI(cities: List<City>)
}