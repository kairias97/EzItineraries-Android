package com.kairias97.ezitineraries.view

import com.kairias97.ezitineraries.model.AttractionSearchResult
import com.kairias97.ezitineraries.model.Category
import com.kairias97.ezitineraries.model.City
import com.kairias97.ezitineraries.model.Country

interface ITouristAttractionSuggestionView : IBaseView {
    fun clearUIControls()
    fun showAttractionSearchUI()
    fun showSuggestionSendProgressMessage()
    fun hideSuggestionSendProgressMessage()
    fun showCountriesListUI(countries: List<Country>)
    fun showCitiesListUI(cities: List<City>)
    fun showCategoriesListUI(countries: List<Category>)
    fun showSelectedAttractionUI(attractionResult: AttractionSearchResult)
    fun showResultMessage(message: String)
    fun showIncompleteInformationMessage()
}