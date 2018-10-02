package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.model.AttractionSearchResult
import com.kairias97.ezitineraries.model.Category
import com.kairias97.ezitineraries.model.City
import com.kairias97.ezitineraries.model.Country
import com.kairias97.ezitineraries.view.ITouristAttractionSuggestionView

interface ITouristAttractionSuggestionPresenter : BasePresenter<ITouristAttractionSuggestionView> {
    fun provideCountriesList()
    fun provideCategoriesList()
    fun onCountrySelected(country: Country)
    fun onAttractionSearchRequested()
    fun onSelectedAttraction(attraction: AttractionSearchResult)
    fun onSendSuggestionRequest(city: City, category: Category)
}