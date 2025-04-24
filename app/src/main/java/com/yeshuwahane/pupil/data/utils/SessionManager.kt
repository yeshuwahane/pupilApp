package com.yeshuwahane.pupil.data.utils

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun setSignedInUserEmail(email: String) {
        prefs.edit().putString("email", email).apply()
    }

    fun getSignedInUserEmail(): String {
        return prefs.getString("email", "") ?: ""
    }

    fun clearSession() {
        prefs.edit() { remove("email") }
    }
}