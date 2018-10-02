package com.kairias97.ezitineraries.data.remote

interface ConnectivityChecker {
    fun hasInternetConnection():Boolean
}