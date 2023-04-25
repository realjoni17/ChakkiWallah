package com.android.chakkiwallah.presentation.productscreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.homescreen.HomeScreenViewModel

@Composable
fun productscreen(productviewmodel: productViewModel = hiltViewModel()) {
    val productdetail = productviewmodel.product.value
  //  productlist(product = productdetail,)
}

@Composable
fun productlist(
   product: Product,
   detailviewmodel:productViewModel
    ) {

     detailviewmodel.setProduct(product)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
    ) {
        run {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.weight(8f)
                    )
                }
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.weight(8f)
                )

            }
        }
    }
}


@Preview
@Composable
fun productScreen() {
  productscreen()
}