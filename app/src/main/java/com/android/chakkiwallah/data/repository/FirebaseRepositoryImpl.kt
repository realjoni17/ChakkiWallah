package com.android.chakkiwallah.data.repository

import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Cart
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore) : FirebaseRepository
{
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

    override fun addproductTOCart(cartProduct: Cart, userId: String): Flow<Resource<Task<Void>>> {
        TODO("Not yet implemented")
    }

    override fun deleteproductfromCart(userId: String, cartProduct: Cart): Resource<Task<Void>> {
        TODO("Not yet implemented")
    }
}





