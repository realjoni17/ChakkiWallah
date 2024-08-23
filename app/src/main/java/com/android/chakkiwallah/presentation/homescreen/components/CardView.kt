package com.android.chakkiwallah.presentation.homescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.productscreen.DetailViewModel


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun CardView(
    product: Product,
    productviewmodel: DetailViewModel = hiltViewModel(),
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                productviewmodel.setProduct(product)
                navController.navigate(Screens.Detail.route)
            },
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp) // Rounded corners for better aesthetics
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Center align items
        ) {
            // Product Image
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = rememberAsyncImagePainter(
                    model = product.image,
                    contentScale = ContentScale.Crop
                ),
                contentDescription = product.name // Use product name for better accessibility
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.h6,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // Handle long names
            )

            // Product Price
            Text(
                text = "₹${product.price}", // Format price
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
        }
    }
}
