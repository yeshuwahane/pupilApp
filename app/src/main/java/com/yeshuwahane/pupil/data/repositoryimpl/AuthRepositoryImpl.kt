package com.yeshuwahane.pupil.data.repositoryimpl

import android.util.Log
import com.yeshuwahane.pupil.data.local.AuthDao
import com.yeshuwahane.pupil.data.utils.SessionManager
import com.yeshuwahane.pupil.data.local.UserEntity
import com.yeshuwahane.pupil.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val dao: AuthDao,
    private val session: SessionManager
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Boolean {
        Log.d("AuthRepo", "Attempting sign in for: $email")
        val user = dao.getUserByEmail(email)

        return if (user != null && user.password == password) {
            Log.d("AuthRepo", "User exists, password matched. Logging in.")
            session.setSignedInUserEmail(email)
            true
        } else if (user == null) {
            Log.d("AuthRepo", "User not found. Creating new user and signing in.")
            dao.insertUser(UserEntity(email, password))
            session.setSignedInUserEmail(email)
            true
        } else {
            Log.d("AuthRepo", "User exists but password mismatch.")
            false
        }
    }

    override suspend fun isUserSignedIn(): Boolean {
        val signedIn = session.getSignedInUserEmail().isNotEmpty()
        Log.d("AuthRepo", "isUserSignedIn: $signedIn")
        return signedIn
    }

    override suspend fun signOut() {
        Log.d("AuthRepo", "Signing out")
        session.clearSession()
    }
}