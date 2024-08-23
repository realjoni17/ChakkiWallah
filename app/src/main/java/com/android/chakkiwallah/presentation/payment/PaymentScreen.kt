package com.android.chakkiwallah.presentation.payment

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.chakkiwallah.domain.model.Product

@Composable
fun PaymentScreen(product: Product) {
    val viewModel: PaymentViewModel = viewModel()
    val LocalContext = LocalContext.current
    Button(onClick = {
       // viewModel.initiatePayment(LocalContext, product)
    }) {
        Text("Pay with Razorpay for ${product.name}")
    }
}