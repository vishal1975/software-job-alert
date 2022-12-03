package com.vishal.softwarejobalert

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window

class CShowProgress() {
    var mDialog: Dialog? = null

    fun showProgress(context: Context) {
        mDialog = Dialog(context)
        mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog!!.setContentView(R.layout.custom_progress_layout)
        mDialog!!.findViewById<View>(R.id.progress_bar).visibility = View.VISIBLE
        mDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog!!.setCanceledOnTouchOutside(false)
        mDialog!!.show()
    }

    fun hideProgress() {
        try {
            if (mDialog != null) {
                mDialog!!.dismiss()
                mDialog = null
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}