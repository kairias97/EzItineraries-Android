package com.kairias97.eztrips.data.remote

import com.kairias97.eztrips.model.*
import retrofit2.Call
import retrofit2.http.*

interface ItinerariesService {
    @GET("categories")
    fun getCategories() : Call<List<Category>>

    @GET("countries")
    fun getCountries(@Query("onlyWithAttractions") onlyWithAttractions : Boolean)
            : Call<List<Country>>

    @GET("countries/{countryId}/cities")
    fun getCities(@Path("countryId") countryId : String,
                  @Query("onlyWithAttractions") onlyWithAttractions : Boolean)
            : Call<List<City>>

    @GET("cities/{cityId}/touristAttractions")
    fun getTouristAttractions(@Path("cityId") cityId : Int)
            : Call<List<TouristAttraction>>

    @POST("suggestions")
    fun sendSuggestion(@Body suggestion : TouristAttractionSuggestion)
            : Call<TouristAttractionSuggestionResult>

    @POST("itineraries")
    fun generateItinerary(@Body itineraryRequest: ItineraryRequest)
            : Call<CalculatedItinerary>





}