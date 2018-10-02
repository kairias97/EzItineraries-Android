package com.kairias97.ezitineraries.data

import com.kairias97.ezitineraries.model.*

interface TripPlansRepository {
    fun getTripPlansHeaders(callback: OperationWithResultCallback<List<TripPlanHeader>>)
    fun getTripPlanInfoById(tripPlanId: Int,
                            callback: OperationWithResultCallback<TripPlan>?)
    fun saveTripPlan(tripPlan: TripPlan,
                                  callback: OperationWithNoResultCallback)
    fun deleteTripPlanById(itineraryId: Int,
                            callback: OperationWithNoResultCallback)
}