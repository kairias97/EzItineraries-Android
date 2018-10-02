package com.kairias97.ezitineraries.presenter

import com.kairias97.ezitineraries.view.ISplashView

interface ISplashPresenter : BasePresenter<ISplashView> {
    fun onSplashLoaded()
}