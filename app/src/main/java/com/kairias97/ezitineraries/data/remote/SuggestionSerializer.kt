package com.kairias97.ezitineraries.data.remote

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.kairias97.ezitineraries.model.TouristAttractionSuggestion
import java.lang.reflect.Type

class SuggestionSerializer : JsonSerializer<TouristAttractionSuggestion> {
    override fun serialize(src: TouristAttractionSuggestion?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        var jsonObj = JsonObject()
        jsonObj.addProperty("cityId", src?.cityId)
        jsonObj.addProperty("categoryId", src?.categoryId)
        src?.attractionSearchResult?.let {
            jsonObj.addProperty("name", src?.attractionSearchResult?.attractionName)
            jsonObj.addProperty("address", src?.attractionSearchResult?.address)
            jsonObj.addProperty("phoneNumber", src?.attractionSearchResult?.phone)
            jsonObj.addProperty("websiteUrl", src?.attractionSearchResult?.websiteUrl)
            jsonObj.addProperty("rating", src?.attractionSearchResult?.rating)
            jsonObj.addProperty("googlePlaceId", src?.attractionSearchResult?.googlePlaceId)
            //setting up the geoposition object
            var geoJsonObj = JsonObject()
            geoJsonObj.addProperty("latitude", it.geolocation?.latitude)
            geoJsonObj.addProperty("longitude", it.geolocation?.longitude)


            jsonObj.add("geoposition", geoJsonObj)
        }
        return jsonObj
    }
}