package com.vishal.softwarejobalert

import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.superNavigate(action: NavDirections){
    try {
        this.findNavController().navigate(action)
    }catch (e : Exception){
        showlog(e.toString())
    }
}
fun showlog(str : String)  {
    Log.d("LogTag", str)
}

fun View.singleClickListener(debounceTime: Long = 1200L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            val timeNow = SystemClock.elapsedRealtime()
            val elapsedTimeSinceLastClick = timeNow - lastClickTime
            showlog( """
                        DebounceTime: $debounceTime
                        Time Elapsed: $elapsedTimeSinceLastClick
                        Is within debounce time: ${elapsedTimeSinceLastClick < debounceTime}
                    """.trimIndent())

            if (elapsedTimeSinceLastClick < debounceTime) {
                showlog("Double click shielded")

                return
            } else {
                showlog("Click happened")
                action()
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
