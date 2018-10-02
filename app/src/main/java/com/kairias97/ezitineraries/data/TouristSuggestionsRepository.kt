package com.kairias97.ezitineraries.data

import com.kairias97.ezitineraries.model.TouristAttractionSuggestion
import com.kairias97.ezitineraries.model.TouristAttractionSuggestionResult

interface TouristSuggestionsRepository {
    fun sendTouristAttractionSuggestion(suggestion: TouristAttractionSuggestion,
                                        callback: OperationWithResultCallback<TouristAttractionSuggestionResult>)
}