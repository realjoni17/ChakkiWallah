package com.android.chakkiwallah.domain.model

import android.os.Parcelable
import androidx.annotation.Nullable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable






data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 1.0,
    var quantity: Int = 1 ,// Default quantity is 1
    val image: String? = "",
    val description: String?  = "",
    val tagline: String? = "",
    val category: String?= ""

)