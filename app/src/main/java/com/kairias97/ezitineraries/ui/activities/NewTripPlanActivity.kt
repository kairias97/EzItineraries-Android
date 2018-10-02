package com.kairias97.ezitineraries.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.Category
import com.kairias97.ezitineraries.model.City
import com.kairias97.ezitineraries.model.Country
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.presenter.INewTripPlanPresenter
import com.kairias97.ezitineraries.ui.fragments.DatePickerFragment
import com.kairias97.ezitineraries.utils.DateUtil
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.INewTripPlanView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class NewTripPlanActivity : DaggerAppCompatActivity(), INewTripPlanView, DatePickerFragment.DatePickerListener {

    private lateinit var mBtnNext: Button
    @Inject
    lateinit var mNewTripPlanPresenter : INewTripPlanPresenter
    private lateinit var mSpinnerCountry: Spinner
    private lateinit var mSpinnerCity: Spinner
    private lateinit var mCountries: List<Country>
    private lateinit var mCities: List<City>
    private lateinit var mDateText: EditText
    private lateinit var mNameText: EditText
    private lateinit var mDescriptionText: EditText
    private lateinit var mDistanceText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trip_plan)

        supportActionBar!!.title = "Nuevo plan de viaje"

        mBtnNext = findViewById(R.id.button_next_activity)
        mSpinnerCountry = findViewById(R.id.spinner_country)
        mSpinnerCity = findViewById(R.id.spinner_city)
        mDateText = findViewById(R.id.edit_text_trip_date)
        mNameText = findViewById(R.id.edit_text_trip_name)
        mDescriptionText = findViewById(R.id.edit_text_trip_description)
        mDistanceText = findViewById(R.id.edit_text_trip_distance)

        mBtnNext.setOnClickListener {
            if(mNewTripPlanPresenter.validateData(mDateText.text.toString(), mNameText.text.toString(), mDistanceText.text.toString())){
                var country:Country = mCountries[mSpinnerCountry.selectedItemPosition]
                var city:City = mCities[mSpinnerCity.selectedItemPosition]
                var date = DateUtil.parseStringToDate(mDateText.text.toString(), "dd/MM/yyyy")
                var name = mNameText.text.toString()
                var description = mDescriptionText.text.toString()
                var distance = mDistanceText.text.toString().toDouble()
                mNewTripPlanPresenter.onNextStepRequest(city, date, name, description, distance)
            }
            else{
                this.toast(R.string.empty_inputs_error)
            }
        }
        mSpinnerCountry.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mNewTripPlanPresenter.onSelectedCountry(mCountries[position])
            }

        }
        mDateText.setOnClickListener {
            val datePickerFragment = DatePickerFragment.newInstance(this)
            datePickerFragment.show(this.supportFragmentManager, "datePicker")
        }
        mNewTripPlanPresenter.takeView(this)
        mNewTripPlanPresenter.provideCountries()
    }

    override fun OnDateSelected(year: Int, month: Int, day: Int) {
        var date = DateUtil.parseDateToFormat(year, month, day, "dd/MM/yyyy")
        mDateText.setText(date)
    }

    override fun onResume() {
        mNewTripPlanPresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mNewTripPlanPresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mNewTripPlanPresenter.dropView()
        super.onDestroy()
    }

    override fun navigateToConfigurePreferences(tripPlan: TripPlan) {
        val intent = Intent(this, TripPlanPreferencesConfigurationActivity::class.java)
        intent.putExtra("tripPlan",tripPlan)
        startActivity(intent)
    }

    override fun showCountriesListUI(countries: List<Country>) {
        mCountries = countries
        var adapter: ArrayAdapter<Country> = ArrayAdapter<Country>(this, android.R.layout.simple_spinner_dropdown_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mNewTripPlanPresenter.onSelectedCountry(countries[0])
        mSpinnerCountry.adapter = adapter

    }

    override fun showCitiesListUI(cities: List<City>) {
        mCities = cities
        var adapter: ArrayAdapter<City> = ArrayAdapter<City>(this, android.R.layout.simple_spinner_dropdown_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerCity.adapter = adapter
    }


    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
    }
}
