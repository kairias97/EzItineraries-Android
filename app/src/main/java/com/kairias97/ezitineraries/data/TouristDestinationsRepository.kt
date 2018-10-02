package com.kairias97.ezitineraries.data

import com.kairias97.ezitineraries.model.City
import com.kairias97.ezitineraries.model.Country


interface TouristDestinationsRepository {
    fun getCountries(onlyWithAttractions: Boolean, callback: OperationWithResultCallback<List<Country>>)
    fun getCities(onlyWithAttractions: Boolean, countryId: String, callback: OperationWithResultCallback<List<City>>)

}