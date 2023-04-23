package com.android.chakkiwallah.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id : Int,
    val category: String,
    val count: Int,
    val description: String,
    val image: String,
    val imageTwo: String,
    val imageThree: String,
    val price: Double,
    val rate: Double,
    val title: String,
    val saleState: Int,
    val isFavorite: Boolean = false,
    val salePrice: Double?
) : Parcelable
