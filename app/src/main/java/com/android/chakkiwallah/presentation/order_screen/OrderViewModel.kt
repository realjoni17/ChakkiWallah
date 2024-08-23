package com.android.chakkiwallah.presentation.order_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Order
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor( private val fireStore: FirebaseFirestore) : ViewModel() {

    private val _orders = MutableStateFlow<Resource<List<Order>>>(Resource.Loading())
    val orders: StateFlow<Resource<List<Order>>> = _orders

    // Function to fetch orders
    fun getOrders(userId: String) {
        viewModelScope.launch {
            _orders.value = Resource.Loading()
            try {
                val ordersSnapshot = fireStore.collection("orders")
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()

                val ordersList = ordersSnapshot.documents.map { document ->
                    Order(
                        id = document.id,
                        userId = document.getString("userId") ?: "",
                        products = (document.get("products") as? List<Map<String, Any>>)?.map { productData ->
                            Product(
                                id = productData["id"] as String,
                                name = productData["name"] as String,
                                price = (productData["price"] as Number).toDouble(),
                                quantity = (productData["quantity"] as? Number)?.toInt() ?: 1
                            )
                        } ?: emptyList(),
                        paymentId = document.getString("paymentId") ?: "",
                        timestamp = document.getLong("timestamp") ?: 0L,
                        isDelivered = document.getBoolean("isDelivered") ?: false
                    )
                }

                _orders.value = Resource.Success(ordersList)
            } catch (e: Exception) {
                _orders.value = Resource.Error(e.message.toString())
            }
        }
    }

    // Function to update delivery status
    fun updateDeliveryStatus(orderId: String, isDelivered: Boolean) {
        viewModelScope.launch {
            fireStore.collection("orders").document(orderId)
                .update("isDelivered", isDelivered)
                .addOnSuccessListener {
                    Log.d("Order", "Delivery status updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("Order", "Error updating delivery status: ${e.message}")
                }
        }
    }
}
