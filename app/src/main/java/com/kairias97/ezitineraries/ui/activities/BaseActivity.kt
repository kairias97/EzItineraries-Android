package com.kairias97.ezitineraries.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
//import com.crashlytics.android.Crashlytics
//import io.fabric.sdk.android.Fabric
import android.app.ProgressDialog
import android.content.DialogInterface
import com.crashlytics.android.Crashlytics
import com.kairias97.ezitineraries.view.IBaseView
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.utils.toast
import io.fabric.sdk.android.Fabric



/**
 * Created by kevin on 22/2/2018.
 */
abstract class BaseActivity: AppCompatActivity(), IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        Fabric.with(this, Crashlytics())

        super.onCreate(savedInstanceState)
    }
    override fun showCommunicationErrorMessage() {
        this.toast(R.string.generic_500_error)
    }
}