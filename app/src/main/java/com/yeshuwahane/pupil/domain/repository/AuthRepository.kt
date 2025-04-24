package com.yeshuwahane.pupil.domain.repository



interface AuthRepository {

    suspend fun signIn(email: String, password: String): Boolean
    suspend fun isUserSignedIn(): Boolean
    suspend fun signOut()
}