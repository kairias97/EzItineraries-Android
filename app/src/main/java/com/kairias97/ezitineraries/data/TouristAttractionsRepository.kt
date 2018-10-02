package com.kairias97.ezitineraries.data

import com.kairias97.ezitineraries.model.Category
import com.kairias97.ezitineraries.model.TouristAttraction

interface TouristAttractionsRepository {
    fun getCategories(callback: OperationWithResultCallback<List<Category>>)
    fun getAttractionsByCity(cityId : Int, callback: OperationWithResultCallback<List<TouristAttraction>>)
}