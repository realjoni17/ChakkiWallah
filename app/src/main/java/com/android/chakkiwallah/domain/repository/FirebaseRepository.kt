package com.android.chakkiwallah.domain.repository

import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.domain.model.Cart
import com.android.chakkiwallah.domain.model.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.Transaction
import com.google.firestore.admin.v1.Index
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun firebaseSignIn(user: AuthUser): Flow<Resource<String>>

    fun firebaseSignUp(user: AuthUser): Flow<Resource<String>>

    suspend fun getAllProducts() :Resource<List<Product>>

    fun addCoffeeToCart(cartProduct: Cart, userId: String): Flow<Resource<Task<Void>>>

    fun deleteCoffeeFromCart(userId: String, cartProduct: Cart): Resource<Task<Void>>

    fun currentUserExist(): Flow<Boolean>

    fun signOut()

    fun currentUser(): FirebaseUser?

    suspend fun addOrders(
        orderList: MutableList<Index.IndexField.Order>, onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

   // fun increaseProductQuantity(documentId: String): Task<Transaction>

    fun userLogOut()

}
