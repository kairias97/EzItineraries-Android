package com.kairias97.ezitineraries.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.di.ActivityScoped
import com.kairias97.ezitineraries.presenter.ITouristAttractionSuggestionPresenter
import com.kairias97.ezitineraries.utils.DialogUtil
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.ITouristAttractionSuggestionView
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import android.support.v4.app.ShareCompat.IntentBuilder
import android.content.Intent
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import android.R.attr.data
import android.app.Activity.RESULT_CANCELED
import com.google.android.gms.location.places.ui.PlaceAutocomplete.getStatus
import com.google.android.gms.location.places.Place
import android.app.Activity.RESULT_OK
import android.widget.*
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Places
import com.kairias97.ezitineraries.model.*
import java.lang.Exception


@ActivityScoped
class SuggestAttractionFragment @Inject constructor() : DaggerFragment(), ITouristAttractionSuggestionView {



    @Inject lateinit var mTouristAttractionSuggestionPresenter : ITouristAttractionSuggestionPresenter

    private lateinit var mSpinnerCountries: Spinner
    private lateinit var mSpinnerCities: Spinner
    private lateinit var mSpinnerCategories: Spinner
    private lateinit var mButtonSearchLocation: Button
    private lateinit var mButtonSend: Button
    private lateinit var mAttractionNameTextView: TextView
    private val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
    protected lateinit var mGeoDataClient: GeoDataClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity.title = getString(R.string.title_fragment_suggestion)
        mGeoDataClient = Places.getGeoDataClient(activity)
        val view = inflater.inflate(R.layout.fragment_suggest_attraction, container, false)
        mSpinnerCountries = view.findViewById(R.id.spinner_country)
        mSpinnerCities = view.findViewById(R.id.spinner_city)
        mSpinnerCategories = view.findViewById(R.id.spinner_category)
        mButtonSearchLocation = view.findViewById(R.id.button_search_location)
        mAttractionNameTextView = view.findViewById(R.id.textView_title_name)
        mButtonSend = view.findViewById(R.id.button_send)
        mButtonSend.setOnClickListener {
            var city = mSpinnerCities.selectedItem as City
            var category = mSpinnerCategories.selectedItem as Category
            mTouristAttractionSuggestionPresenter.onSendSuggestionRequest(city, category)
        }
        mSpinnerCountries.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var country = (mSpinnerCountries.adapter as ArrayAdapter<Country>).getItem(position)
                mTouristAttractionSuggestionPresenter.onCountrySelected(country)
            }

        }
        mTouristAttractionSuggestionPresenter.takeView(this)
        mTouristAttractionSuggestionPresenter.provideCountriesList()
        mTouristAttractionSuggestionPresenter.provideCategoriesList()

        mButtonSearchLocation.setOnClickListener {
            mTouristAttractionSuggestionPresenter.onAttractionSearchRequested()
        }


        return view
    }


    override fun onResume() {
        mTouristAttractionSuggestionPresenter.takeView(this)
        super.onResume()
    }
    override fun onDetach() {
        mTouristAttractionSuggestionPresenter.dropView()
        super.onDetach()

    }

    override fun onDestroy() {
        mTouristAttractionSuggestionPresenter.dropView()
        super.onDestroy()
    }

    override fun clearUIControls() {
        if(mSpinnerCountries.adapter.count > 0) {
            mSpinnerCountries.setSelection(0)
        }
        if (mSpinnerCategories.adapter.count > 0) {
            mSpinnerCategories.setSelection(0)
        }
        mAttractionNameTextView.text = ""

    }
    override fun showIncompleteInformationMessage() {
        activity.toast(R.string.message_incomplete_suggestion)
    }
    override fun showAttractionSearchUI() {
        try {
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(activity)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            // TODO: Handle the error.
        } catch (e: GooglePlayServicesNotAvailableException) {
            // TODO: Handle the error.
        } catch (ex: Exception) {
            //TODO: Handle generic exception
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode === RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(activity, data)
                var searchResult = AttractionSearchResult()
                searchResult.address = place?.address?.toString() ?: ""
                searchResult.attractionName = place?.name?.toString() ?: ""
                searchResult.geolocation = Geolocation()
                searchResult.geolocation!!.latitude = place?.latLng?.latitude
                searchResult.geolocation!!.longitude = place?.latLng?.longitude
                searchResult.googlePlaceId = place?.id ?: ""
                searchResult.phone = place.phoneNumber?.toString()
                searchResult.rating = place.rating?.toDouble()
                searchResult.websiteUrl = place?.websiteUri?.toString()
                mTouristAttractionSuggestionPresenter.onSelectedAttraction(searchResult)
            } else if (resultCode === PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(activity, data)

            } else if (resultCode === RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    override fun showSuggestionSendProgressMessage() {
        DialogUtil.showDialog(activity,getString(R.string.message_sending_suggestion))
    }

    override fun hideSuggestionSendProgressMessage() {
        DialogUtil.hideDialog()
    }

    override fun showCountriesListUI(countries: List<Country>) {
        var adapter: ArrayAdapter<Country> = ArrayAdapter<Country>(activity, android.R.layout.simple_spinner_dropdown_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerCountries.adapter = adapter
        //To fetch the cities
        if (countries.isNotEmpty()) {
            mTouristAttractionSuggestionPresenter.onCountrySelected(countries[0])
        }
    }

    override fun showCitiesListUI(cities: List<City>) {
        var adapter = ArrayAdapter<City>(activity, android.R.layout.simple_spinner_dropdown_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerCities.adapter = adapter
    }

    override fun showCategoriesListUI(countries: List<Category>) {
        var adapter = ArrayAdapter<Category>(activity, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerCategories.adapter = adapter
    }

    override fun showSelectedAttractionUI(attractionResult: AttractionSearchResult) {
        mAttractionNameTextView.text = attractionResult.attractionName
    }

    override fun showResultMessage(message: String) {
        activity.toast(message)
    }

    override fun showCommunicationErrorMessage() {
        activity.toast(R.string.generic_500_error)
    }

}
