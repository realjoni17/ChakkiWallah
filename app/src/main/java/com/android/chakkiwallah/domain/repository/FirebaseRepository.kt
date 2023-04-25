package com.android.chakkiwallah.domain.repository

import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Cart
import com.android.chakkiwallah.domain.model.Product
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    suspend fun getAllProducts() :Resource<List<Product>>

    fun addproductTOCart(cartProduct: Cart, userId: String): Flow<Resource<Task<Void>>>

    fun deleteproductfromCart(userId: String, cartProduct: Cart): Resource<Task<Void>>

}
