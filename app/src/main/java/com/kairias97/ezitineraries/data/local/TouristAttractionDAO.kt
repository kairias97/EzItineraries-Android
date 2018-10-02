package com.kairias97.ezitineraries.data.local

import com.kairias97.ezitineraries.data.OperationWithNoResultCallback
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.model.TouristAttraction

interface TouristAttractionDAO {
    fun saveAll(attractions : List<TouristAttraction>, callback: OperationWithNoResultCallback?)
    fun getAttractionsByCity(cityId: Int, callback: OperationWithResultCallback<List<TouristAttraction>>?)
}