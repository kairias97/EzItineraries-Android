package com.kairias97.ezitineraries.data.local

import com.kairias97.ezitineraries.data.OperationWithNoResultCallback
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.model.TripPlan
import com.kairias97.ezitineraries.model.TripPlanHeader

interface TripPlanDAO {
    fun saveTripPlan(tripPlan : TripPlan, callback: OperationWithNoResultCallback?)
    fun deleteTripPlanById(tripPlanId : Int, callback: OperationWithNoResultCallback?)
    fun getTripPlanById(tripPlanId: Int, callback: OperationWithResultCallback<TripPlan>?)
    fun getTripPlansHeaders(callback: OperationWithResultCallback<List<TripPlanHeader>>?)
}