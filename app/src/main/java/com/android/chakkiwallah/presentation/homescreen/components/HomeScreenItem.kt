package com.android.chakkiwallah.presentation.homescreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.chakkiwallah.R
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.bottom_navbar.BottomNavItem
import com.android.chakkiwallah.presentation.bottom_navbar.NavBar
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.productscreen.DetailViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenItem(product: List<Product>,
                   navController: NavController,
                   productviewmodel: DetailViewModel)
{

    LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(product.size) { item ->
                CardView(product = product[item],
                    navController = navController,
                    productviewmodel = productviewmodel)
            }
        }
    }


