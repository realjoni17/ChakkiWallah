package com.android.chakkiwallah.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: FirebaseRepository) : ViewModel() {

  private val _addProductToCartResult = MutableStateFlow<Resource<Task<Void>>>(Resource.Loading())
  val addProductToCartResult: StateFlow<Resource<Task<Void>>> = _addProductToCartResult
  private val _cartItems = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
  val cartItems: StateFlow<Resource<List<Product>>> get() = _cartItems

  fun addProductToCart(cartProduct: Product, userId: String) {
    viewModelScope.launch {
      repository.addProductToCart(cartProduct, userId)
        .onStart {
          _addProductToCartResult.value = Resource.Loading()
        }
        .collect { resource ->
          _addProductToCartResult.value = resource
        }
    }
  }
  fun fetchCartItems(userId: String) {
    viewModelScope.launch {
      repository.getCartItems(userId)
        .onEach { resource ->
          _cartItems.value = resource
        }
        .launchIn(this)
    }
  }

  fun updateQuantity(userId: String, productId: String, newQuantity: Int) {
    viewModelScope.launch {
      repository.updateProductQuantity(userId, productId, newQuantity)
        .collect { resource ->
          // Handle success or error if needed
        }
    }
  }
  fun calculateTotalPrice(products: List<Product>): Double {
    return products.sumOf { it.price * it.quantity }
  }

  fun getTotalPrice(products: List<Product>): Double {
    return calculateTotalPrice(products)
  }
}






