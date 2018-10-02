package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.presenter.ISplashPresenter
import com.kairias97.ezitineraries.view.ISplashView
import javax.inject.Inject

class SplashPresenter @Inject constructor() : ISplashPresenter {

    private var mSplashView: ISplashView? = null

    override fun onSplashLoaded() {
        mSplashView?.navigateToMainScreen()
    }

    override fun takeView(view: ISplashView) {
        mSplashView = view
    }

    override fun dropView() {
        mSplashView = null
    }
}