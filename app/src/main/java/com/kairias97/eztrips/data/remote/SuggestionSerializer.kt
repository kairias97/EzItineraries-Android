package com.kairias97.eztrips.data.remote

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.kairias97.eztrips.model.TouristAttractionSuggestion
import java.lang.reflect.Type

class SuggestionSerializer : JsonSerializer<TouristAttractionSuggestion> {
    override fun serialize(src: TouristAttractionSuggestion?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        var jsonObj = JsonObject()
        jsonObj.addProperty("cityId", src?.cityId)
        jsonObj.addProperty("categoryId", src?.categoryId)
        src?.attractionSearchResult?.let {
            jsonObj.addProperty("name", src?.categoryId)
            jsonObj.addProperty("address", src?.categoryId)
            jsonObj.addProperty("phoneNumber", src?.categoryId)
            jsonObj.addProperty("websiteUrl", src?.categoryId)
            jsonObj.addProperty("rating", src?.categoryId)
            jsonObj.addProperty("googlePlaceId", src?.categoryId)
            //setting up the geoposition object
            var geoJsonObj = JsonObject()
            geoJsonObj.addProperty("latitude", it.geolocation?.latitude)
            geoJsonObj.addProperty("longitude", it.geolocation?.longitude)


            jsonObj.add("geoposition", geoJsonObj)
        }
        return jsonObj
    }
}