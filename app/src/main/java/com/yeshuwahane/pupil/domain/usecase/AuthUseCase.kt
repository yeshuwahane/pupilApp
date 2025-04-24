package com.yeshuwahane.pupil.domain.usecase

import com.yeshuwahane.pupil.data.utils.DataResource
import com.yeshuwahane.pupil.domain.repository.AuthRepository
import javax.inject.Inject



// not using this right now, if planning to add google,apple,facebook,etc auth in future
class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun signIn(email: String, password: String): DataResource<Unit> {
        return try {
            if (repository.signIn(email, password)) {
                DataResource.success(Unit)
            } else {
                DataResource.error(Throwable("Invalid credentials"))
            }
        } catch (e: Exception) {
            DataResource.error(e)
        }
    }

    suspend fun signOut() {
        repository.signOut()
    }

    suspend fun isUserSignedIn(): Boolean {
        return repository.isUserSignedIn()
    }
}
