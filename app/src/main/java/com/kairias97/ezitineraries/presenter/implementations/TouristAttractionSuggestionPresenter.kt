package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.data.TouristAttractionsRepository
import com.kairias97.ezitineraries.data.TouristDestinationsRepository
import com.kairias97.ezitineraries.data.TouristSuggestionsRepository
import com.kairias97.ezitineraries.model.*
import com.kairias97.ezitineraries.presenter.ITouristAttractionSuggestionPresenter
import com.kairias97.ezitineraries.view.ITouristAttractionSuggestionView
import javax.inject.Inject

class TouristAttractionSuggestionPresenter @Inject constructor(private val mSuggestionsRepository
                                                               : TouristSuggestionsRepository,
                                                               private val mTouristDestinationsRepository
                                                               : TouristDestinationsRepository,
                                                               private val mTouristAttractionsRepository
                                                               : TouristAttractionsRepository)
    : ITouristAttractionSuggestionPresenter {


    private var mTouristAttractionSuggestionView : ITouristAttractionSuggestionView? = null
    private var mSelectedAttraction : AttractionSearchResult? = null

    override fun provideCountriesList() {
        mTouristDestinationsRepository.getCountries(false,
                object:OperationWithResultCallback<List<Country>> {
                    override fun onSuccess(result: List<Country>) {
                        mTouristAttractionSuggestionView?.showCountriesListUI(result)

                    }

                    override fun onError() {

                        mTouristAttractionSuggestionView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun provideCategoriesList() {
        mTouristAttractionsRepository.getCategories(object:OperationWithResultCallback<List<Category>> {
            override fun onSuccess(result: List<Category>) {
                mTouristAttractionSuggestionView?.showCategoriesListUI(result)
            }

            override fun onError() {
                mTouristAttractionSuggestionView?.showCommunicationErrorMessage()
            }

        })
    }

    override fun onCountrySelected(country: Country) {
        mTouristDestinationsRepository.getCities(false, country.isoNumericCode!!,
                object:OperationWithResultCallback<List<City>> {
            override fun onSuccess(result: List<City>) {
                mTouristAttractionSuggestionView?.showCitiesListUI(result)
            }

            override fun onError() {
                mTouristAttractionSuggestionView?.showCommunicationErrorMessage()
            }

        })
    }

    override fun onAttractionSearchRequested() {
        mTouristAttractionSuggestionView?.showAttractionSearchUI()
    }

    override fun onSelectedAttraction(attraction: AttractionSearchResult) {
        mSelectedAttraction = attraction
        mTouristAttractionSuggestionView?.showSelectedAttractionUI(attraction)
    }

    override fun onSendSuggestionRequest(city: City, category: Category) {

        if (mSelectedAttraction == null) {
            mTouristAttractionSuggestionView?.showIncompleteInformationMessage()
        } else {
            var suggestion = TouristAttractionSuggestion()
            suggestion.categoryId = category.id
            suggestion.cityId = city.cityId
            suggestion.attractionSearchResult = this.mSelectedAttraction

            mTouristAttractionSuggestionView?.showSuggestionSendProgressMessage()
            mSuggestionsRepository.sendTouristAttractionSuggestion(suggestion,
                    object:OperationWithResultCallback<TouristAttractionSuggestionResult> {
                        override fun onSuccess(result: TouristAttractionSuggestionResult) {

                            mTouristAttractionSuggestionView?.hideSuggestionSendProgressMessage()
                            if(result.isSuccessful == true) {
                                mSelectedAttraction = null
                                mTouristAttractionSuggestionView?.clearUIControls()
                            }
                            mTouristAttractionSuggestionView?.showResultMessage(result.message ?: "")
                        }

                        override fun onError() {

                            mTouristAttractionSuggestionView?.hideSuggestionSendProgressMessage()
                        }

                    })
        }

    }

    override fun takeView(view: ITouristAttractionSuggestionView) {
        mTouristAttractionSuggestionView = view
    }

    override fun dropView() {
        mTouristAttractionSuggestionView = null
    }
}