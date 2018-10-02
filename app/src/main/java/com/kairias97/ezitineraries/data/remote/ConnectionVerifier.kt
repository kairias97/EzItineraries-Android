package com.kairias97.ezitineraries.data.remote

import android.net.ConnectivityManager
import javax.inject.Inject

class ConnectionVerifier @Inject constructor(val connectivityManager: ConnectivityManager)
    : ConnectivityChecker{
    override fun hasInternetConnection(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}