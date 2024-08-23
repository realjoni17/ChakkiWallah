package com.android.chakkiwallah.presentation.payment


import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel @Inject constructor(private val userRepository : FirebaseRepository,
                                           private val fireStore: FirebaseFirestore) : ViewModel() {


    fun initiatePayment(context: Context, product: Product, totalPrice: Int) {
        viewModelScope.launch {
            val options = createPaymentOptions(product, totalPrice * 100)
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_3pgRxccXsWDpNl") // Replace with your Razorpay Key ID

            try {
                checkout.open(context as Activity, options)
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun createPaymentOptions(product: Product, totalPrice: Int): JSONObject {
        return JSONObject().apply {
            put("name", product.name)
            put("description", product.description ?: "Purchase of ${product.name}")
            put("currency", "INR")
           /* put("amount", (product.price * product.quantity * 100).toInt()) */// Amount in paise
            put("amount", (totalPrice).toInt())
            put("image", product.image)
        }
    }

    fun savePaymentData(paymentData: JSONObject) {
        val userId = userRepository.uid()
        if (userId != null) {
            userRepository.savePaymentData(paymentData, userId)
        }
    }

    fun transferCartToOrders(userId: String, paymentId: String) {
        // Get cart items
        userRepository.getCartItems(userId).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    val products = resource.data ?: emptyList()
                    if (products.isNotEmpty()) {
                        // Create an order document
                        val orderData = mapOf(
                            "userId" to userId,
                            "products" to products.map { product ->
                                mapOf(
                                    "id" to product.id,
                                    "name" to product.name,
                                    "price" to product.price,
                                    "quantity" to product.quantity
                                )
                            },
                            "paymentId" to paymentId,
                            "timestamp" to System.currentTimeMillis()
                        )

                        // Store the order in Firestore
                        fireStore.collection("orders").add(orderData)
                            .addOnSuccessListener {
                                Log.d("Order", "Order placed successfully")
                                // Optionally clear the cart or provide feedback to the user
                                clearCart(userId)
                            }
                            .addOnFailureListener { e ->
                                Log.e("Order", "Error placing order: ${e.message}")
                            }
                    }
                }

                is Resource.Error<*> -> {
                    Log.e("Cart", "Error fetching cart items: ${resource.message}")
                }

                is Resource.Loading -> {
                    // Handle loading state if needed
                }
            }
        }.launchIn(viewModelScope) // Ensure you're using a coroutine scope
    }


    private fun clearCart(userId: String) {
        viewModelScope.launch {
            fireStore.collection("user_collection").document(userId)
                .update("cartProducts", emptyList<Any>())
                .addOnSuccessListener {
                    Log.d("Cart", "Cart cleared successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("Cart", "Error clearing cart: ${e.message}")
                }
        }
    }
}
