package com.android.chakkiwallah.data.repository

import com.android.chakkiwallah.common.Constants.USER_COLLECTION
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.domain.repository.AuthRepository
import com.google.firebase.auth.*
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {
    private fun FirebaseUser.toUser() = mapOf(
        "displayName" to displayName,
        "email" to email,
        "photoUrl" to photoUrl?.toString(),
        "createDate" to serverTimestamp()
    )


    override suspend fun addUserToFireStore() {
        firebaseAuth.currentUser?.apply {
            val user = toUser()
            fireStore.collection(USER_COLLECTION).document(uid).set(user).await()
        }
    }

        override fun signInWithCredential(
            credentials: AuthCredential,
            user: AuthUser
        ): Flow<Resource<AuthResult>> {
            return flow {
                emit(Resource.Loading())
                val result = firebaseAuth.signInWithCredential(credentials).await()
                val userUid = result.user?.uid
                fireStore.collection(USER_COLLECTION).document(userUid!!).set(
                    addUserToFireStore()
                )
                emit(Resource.Success(result))
            }.catch {
                emit(Resource.Error(it.message.toString()))
            }
        }


}

