package com.yeshuwahane.pupil.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.yeshuwahane.pupil.di.PupilmeshApp
import dagger.hilt.android.qualifiers.ApplicationContext




object NetworkUtil {
    fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}

