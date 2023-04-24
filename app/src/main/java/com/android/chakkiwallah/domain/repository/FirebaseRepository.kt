package com.android.chakkiwallah.domain.repository

import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Product

interface FirebaseRepository {

    suspend fun getAllProducts() :Resource<List<Product>>

}
