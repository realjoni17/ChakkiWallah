package com.android.chakkiwallah.data.repository

import com.android.chakkiwallah.common.Constants.USER_COLLECTION
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.domain.model.Cart
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
private val firebaseAuth: FirebaseAuth) : FirebaseRepository {
    override fun firebaseSignIn(user: AuthUser): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        trySend(Resource.Loading())
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).addOnSuccessListener {
            trySend(Resource.Success("Login Successful"))
        }.addOnFailureListener {
            trySend(Resource.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun firebaseSignUp(user: AuthUser): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                val userUid = it.user?.uid
                fireStore.collection(USER_COLLECTION).document(userUid!!).set(user)
                trySend(Resource.Success("Signup Successful"))
            }.addOnFailureListener {
                trySend(Resource.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }

    override suspend fun getAllProducts(): Resource<List<Product>> {
        val result: List<Product>
        return try {
            result = fireStore.collection("products").get().await().map {
                it.toObject(Product::class.java)
            }
            Resource.Success(result)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

    override fun currentUserExist(): Flow<Boolean> {
        return flow {
            emit(firebaseAuth.currentUser != null)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}





