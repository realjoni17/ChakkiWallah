package com.android.chakkiwallah.presentation.productscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.chakkiwallah.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class productViewModel@Inject constructor(

) : ViewModel() {
    private val _product = mutableStateOf(Product())
    val product: State<Product> = _product

    fun setProduct(product: Product) {
        _product.value = product
    }
}