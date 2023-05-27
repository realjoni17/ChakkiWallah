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
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(100.dp)
                .width(100.dp),
            painter = rememberAsyncImagePainter(
                model = product.image,
                contentScale = ContentScale.Crop
            ),
            contentDescription = "Items"
        )
            Text(
                text = product.name,
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
            Text(
                text = product.price,
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
        }
    }
}