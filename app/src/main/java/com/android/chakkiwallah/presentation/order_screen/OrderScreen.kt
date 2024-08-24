package com.android.chakkiwallah.presentation.order_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Order
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.animation.LoadingScreen
import com.android.chakkiwallah.presentation.cart.CartViewModel
import com.android.chakkiwallah.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



@Composable
fun OrderListScreen(
    viewModel: OrderViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val ordersState by viewModel.orders.collectAsState()
    val userId = loginViewModel.uid!!

    LaunchedEffect(Unit) {
        viewModel.getOrders(userId)
    }

    when (ordersState) {
        is Resource.Loading -> {
          LoadingScreen()
        }
        is Resource.Success -> {
            val orders = (ordersState as Resource.Success).data ?: emptyList()
            OrdersList(orders, viewModel)
        }
        is Resource.Error -> {
            Text(text = "Error: ${(ordersState as Resource.Error).message}")
        }
    }
}

@Composable
fun OrdersList(orders: List<Order>, viewModel: OrderViewModel) {
    LazyColumn {
        // Add a header item
        item {
            Text(
                text = "Your Orders",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Add order items
        items(orders) { order ->
            OrderItem(order = order, onDeliveryStatusChanged = { isDelivered ->
                viewModel.updateDeliveryStatus(order.id, isDelivered)
            })
        }
    }
}


@Composable
fun OrderItem(order: Order, onDeliveryStatusChanged: (Boolean) -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = "Delivered: ${if (order.isDelivered) "Yes" else "No"}")

            Text(text = "Products:", style = MaterialTheme.typography.h6)

            // Use Column instead of LazyColumn for products
            Column {
                order.products.forEach { product ->
                    ProductItem(product)
                }
            }

            Row(modifier = Modifier.padding(top = 8.dp)) {


            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Column(modifier = Modifier.padding(4.dp)) {
        Text(text = "Name: ${product.name}")
        Text(text = "Price: ₹${product.price}")
        Text(text = "Quantity: ${product.quantity}")
        Divider()
    }
}
