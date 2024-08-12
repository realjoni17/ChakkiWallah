package com.android.chakkiwallah.presentation.productscreen.components

import com.android.chakkiwallah.presentation.cart.CartViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.navigation.Screens
import kotlinx.coroutines.flow.onEach


@Composable
fun Productlist(
    product: Product,
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uid = loginViewModel.uid!!
    val addProductToCartResult by cartViewModel.addProductToCartResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .navigationBarsPadding()
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = product.image,
                contentScale = ContentScale.Crop
            ),
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Text(
            text = product.name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = product.price.toString(), // Ensure price is a String
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = product.description!!,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick = {
                    cartViewModel.addProductToCart(Product(
                        description = product.description,
                        name = product.name,
                        category = product.category,
                        image = product.image,
                        price = product.price,
                        tagline = product.tagline,
                          id = uid
                    ), uid)
                    navController.navigate(Screens.Cart.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .offset(y = -36.dp)
            ) {
                Text(text = "Add to Cart")
            }
        }
    }

    // Handle the result of adding to the cart
    LaunchedEffect(addProductToCartResult) {
        when (addProductToCartResult) {
            is Resource.Loading<*> -> {
                // Show loading indicator if needed
            }
            is Resource.Success<*> -> {
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show()
            }
            is Resource.Error<*> -> {
                Toast.makeText(context, addProductToCartResult.message, Toast.LENGTH_SHORT).show()

            }
        }
    }
}



