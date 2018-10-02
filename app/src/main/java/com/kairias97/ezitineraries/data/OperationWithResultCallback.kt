package com.kairias97.ezitineraries.data

interface OperationWithResultCallback<T> {
    fun onSuccess(result : T)
    fun onError()
}