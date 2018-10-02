package com.kairias97.ezitineraries.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.presenter.ISplashPresenter
import com.kairias97.ezitineraries.utils.TimedTaskHandler
import com.kairias97.ezitineraries.utils.toast
import com.kairias97.ezitineraries.view.ISplashView
import dagger.android.support.DaggerAppCompatActivity
import io.fabric.sdk.android.Fabric
import java.lang.RuntimeException
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity(), ISplashView {

    private val mSplashTimeOut: Int = 1000

    @Inject
    lateinit var mSplashPresenter : ISplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide()



        mSplashPresenter.takeView(this)
        mSplashPresenter.onSplashLoaded()
    }
    fun navigateToMain(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK  or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onResume() {
        mSplashPresenter.takeView(this)
        super.onResume()
    }

    override fun onPause() {
        mSplashPresenter.dropView()
        super.onPause()
    }

    override fun onDestroy() {
        mSplashPresenter.dropView()
        super.onDestroy()
    }

    override fun navigateToMainScreen() {
        val timedTask = TimedTaskHandler()
        timedTask.executeTimedTask({ navigateToMain() }, mSplashTimeOut.toLong())
    }

    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
    }
}
