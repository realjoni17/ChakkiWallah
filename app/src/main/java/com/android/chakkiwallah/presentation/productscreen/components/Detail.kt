package com.android.chakkiwallah.presentation.productscreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.chakkiwallah.presentation.productscreen.components.Productlist

@Composable
fun Detail(viewModel : DetailViewModel = hiltViewModel(),
           navController: NavController)
{
     val productdetail =  viewModel.product.value
   Productlist(product = productdetail, navController = navController )
}
