package com.kairias97.ezitineraries.utils

import android.app.ProgressDialog
import android.content.Context
import com.kairias97.ezitineraries.R

class DialogUtil
{
    companion object {
        protected var mProgressDialog: ProgressDialog? = null
        fun showDialog(context: Context, message: String){
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog(context)
            }
            mProgressDialog?.setMessage(message)
            mProgressDialog?.isIndeterminate = true
            mProgressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.show()
        }
        fun hideDialog(){
            if (mProgressDialog != null) {
                mProgressDialog?.dismiss()
                mProgressDialog = null
            }
        }
    }
}