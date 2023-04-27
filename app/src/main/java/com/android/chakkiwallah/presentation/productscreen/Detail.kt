package com.android.chakkiwallah.presentation.productscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.homescreen.HomeScreenViewModel

@Composable
fun Detail(productviewmodel : productViewModel = hiltViewModel(),
navController: NavController)
{
     val productdetail =  productviewmodel.product.value
   productlist(product = productdetail )
}

@Composable
fun productlist(
   product: Product,
  // detailviewmodel:productViewModel,
   homescreenviewModel:HomeScreenViewModel = hiltViewModel(),
  // navController: NavController
    ) {

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

/**
@Preview
@Composable
fun productScreen() {
  Detail()
}
        */