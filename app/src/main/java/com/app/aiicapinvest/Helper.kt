package com.app.aiicapinvest

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

object Helper {

    fun Context.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
        val networkInfo = connectivityManager.activeNetworkInfo //2
        return networkInfo != null && networkInfo.isConnected //3
    }

}