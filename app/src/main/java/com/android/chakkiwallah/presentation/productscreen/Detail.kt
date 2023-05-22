package com.android.chakkiwallah.presentation.productscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.chakkiwallah.domain.model.Product

@Composable
fun Detail(productviewmodel : DetailViewModel = hiltViewModel(),
           navController: NavController)
{
     val productdetail =  productviewmodel.product.value
   productlist(product = productdetail, navController = navController )
}

@Composable
fun productlist(
    product: Product, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )
            Text(
                text = product.price,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )


            Text(
                text = product.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
           , horizontalAlignment =Alignment.End ) {
            Button(
                onClick = {}, modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text(text = "Add to Cart")
            }
        }

        }
    }




