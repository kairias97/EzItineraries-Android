package com.kairias97.ezitineraries.data

import com.kairias97.ezitineraries.data.local.CalculatedItineraryDAO
import com.kairias97.ezitineraries.data.local.TouristAttractionDAO
import com.kairias97.ezitineraries.data.local.TripPlanDAO
import com.kairias97.ezitineraries.data.remote.ConnectivityChecker
import com.kairias97.ezitineraries.data.remote.ItinerariesService
import com.kairias97.ezitineraries.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class CentralRepository @Inject constructor(val itinerariesService: ItinerariesService,
                                            val tripPlanDAO: TripPlanDAO,
                                            val touristAttractionDAO: TouristAttractionDAO,
                                            val calculatedItineraryDAO: CalculatedItineraryDAO,
                                            val connectionVerifier: ConnectivityChecker)
    :   TouristDestinationsRepository,
        TripPlansRepository,
        TouristAttractionsRepository,
        ItinerariesRepository,
        TouristSuggestionsRepository
    {
        override fun sendTouristAttractionSuggestion(suggestion: TouristAttractionSuggestion, callback: OperationWithResultCallback<TouristAttractionSuggestionResult>) {
            itinerariesService.sendSuggestion(suggestion)
                    .enqueue(object:Callback<TouristAttractionSuggestionResult> {
                        override fun onFailure(call: Call<TouristAttractionSuggestionResult>, t: Throwable) {
                            callback.onError()
                        }

                        override fun onResponse(call: Call<TouristAttractionSuggestionResult>, response: Response<TouristAttractionSuggestionResult>) {
                            if (response.code() == 200) {
                                callback.onSuccess(response.body()!!)
                            } else {
                                callback.onError()
                            }
                        }

                    })
        }

        override fun getItinerariesHeaders(callback: OperationWithResultCallback<List<CalculatedItineraryHeader>>) {
            calculatedItineraryDAO.getItinerariesHeaders(callback)
        }

        override fun getItineraryInfoById(itineraryId: Int,
                                          callback: OperationWithResultCallback<CalculatedItinerary>) {
            calculatedItineraryDAO.getItineraryById(itineraryId, callback)
        }

        override fun generateItineraryProposal(tripPlanId: Int,
                                               callback: OperationWithResultCallback<CalculatedItinerary>) {

            //Fetching the tripPlanInfo
            tripPlanDAO.getTripPlanById(tripPlanId, object:OperationWithResultCallback<TripPlan> {
                override fun onSuccess(tripPlan: TripPlan) {
                    //Setup of the request
                    var itineraryRequest = ItineraryRequest()
                    itineraryRequest.cityId = tripPlan.city!!.cityId
                    itineraryRequest.maxDistance = tripPlan.restriction?.maxDistance
                    itineraryRequest.preferences = tripPlan.preferences?.map {
                        var categoryPreference = CategoryPreference()
                        categoryPreference.categoryId = it.category?.id
                        categoryPreference.weight = it.assignedWeight
                        categoryPreference
                    }
                    itinerariesService.generateItinerary(itineraryRequest)
                            .enqueue(object: Callback<CalculatedItinerary>{
                                override fun onFailure(call: Call<CalculatedItinerary>, t: Throwable) {
                                    callback.onError()
                                }

                                override fun onResponse(call: Call<CalculatedItinerary>, response: Response<CalculatedItinerary>) {
                                    if (response.code() == 200) {
                                        var calculatedItinerary = response.body()!!
                                        calculatedItinerary.itineraryPreferences =
                                                tripPlan.preferences?.map {
                                                    var itineraryPreference = ItineraryTouristPreference()
                                                    itineraryPreference.category = it.category
                                                    itineraryPreference.weight = it.assignedWeight
                                                    itineraryPreference
                                                }
                                        //Setup of the configuration data based on the trip plan
                                        calculatedItinerary.city = tripPlan.city
                                        calculatedItinerary.restriction = tripPlan.restriction
                                        calculatedItinerary.visitDate = tripPlan.date

                                        callback.onSuccess(calculatedItinerary)
                                    } else {
                                        callback.onError()
                                    }
                                }

                            })
                }

                override fun onError() {
                    callback.onError()
                }

            })
        }

        override fun saveItinerary(itinerary: CalculatedItinerary
                                   , callback: OperationWithNoResultCallback) {
            calculatedItineraryDAO.saveItinerary(itinerary, callback)
        }

        override fun deleteItineraryById(itineraryId: Int, callback: OperationWithNoResultCallback) {
            calculatedItineraryDAO.deleteItineraryById(itineraryId, callback)
        }

        override fun getTripPlansHeaders(callback: OperationWithResultCallback<List<TripPlanHeader>>) {
            tripPlanDAO.getTripPlansHeaders(callback)
        }

        override fun getTripPlanInfoById(tripPlanId: Int, callback: OperationWithResultCallback<TripPlan>?) {
            tripPlanDAO.getTripPlanById(tripPlanId, callback)
        }

        override fun saveTripPlan(tripPlan: TripPlan, callback: OperationWithNoResultCallback) {
            tripPlanDAO.saveTripPlan(tripPlan, callback)
        }

        override fun deleteTripPlanById(itineraryId: Int, callback: OperationWithNoResultCallback) {
            tripPlanDAO.deleteTripPlanById(itineraryId, callback)
        }

        override fun getCategories(callback: OperationWithResultCallback<List<Category>>) {
            itinerariesService.getCategories().enqueue(object:Callback<List<Category>> {
                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    callback.onError()
                }

                override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                    if (response.code() == 200) {
                        callback.onSuccess(response.body()!!)
                    } else {
                        callback.onError()
                    }
                }

            })
        }

        override fun getAttractionsByCity(cityId: Int, callback: OperationWithResultCallback<List<TouristAttraction>>) {
            if (connectionVerifier.hasInternetConnection()) {
                itinerariesService.getTouristAttractions(cityId)
                        .enqueue(object: Callback<List<TouristAttraction>> {
                            override fun onFailure(call: Call<List<TouristAttraction>>, t: Throwable) {
                                touristAttractionDAO.getAttractionsByCity(cityId, callback)

                            }

                            override fun onResponse(call: Call<List<TouristAttraction>>, response: Response<List<TouristAttraction>>) {
                                if(response.code() == 200) {
                                    var attractions = response.body()!!
                                    touristAttractionDAO.saveAll(attractions, null)
                                    callback.onSuccess(attractions)
                                } else {
                                    callback.onError()
                                }
                            }

                        })

            } else {
                touristAttractionDAO.getAttractionsByCity(cityId, callback)
            }
        }

        override fun getCountries(onlyWithAttractions: Boolean, callback: OperationWithResultCallback<List<Country>>) {
            itinerariesService.getCountries(onlyWithAttractions)
                    .enqueue(object: Callback<List<Country>> {
                        override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                            callback.onError()
                        }

                        override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                            if (response.code() == 200) {
                                callback.onSuccess(response.body()!!)
                            } else {
                                callback.onError()
                            }

                        }

                    })
        }

        override fun getCities(onlyWithAttractions: Boolean, countryId: String, callback: OperationWithResultCallback<List<City>>) {
            itinerariesService.getCities(countryId, onlyWithAttractions)
                    .enqueue(object:Callback<List<City>> {
                        override fun onFailure(call: Call<List<City>>, t: Throwable) {
                            callback.onError()
                        }

                        override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                            if (response.code() == 200) {
                                callback.onSuccess(response.body()!!)
                            } else {
                                callback.onError()
                            }
                        }

                    })
        }

    }