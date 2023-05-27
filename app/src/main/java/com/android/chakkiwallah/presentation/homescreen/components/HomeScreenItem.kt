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
                   productviewmodel: DetailViewModel
)
{

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ChakkiWallah") }) },
        bottomBar = {
            NavBar(items = listOf(
                BottomNavItem("Home",
                    route = Screens.HomeScreen.route,
                    icon = R.drawable.icons8_home),
                BottomNavItem("Cart",
                    route = Screens.Cart.route,
                    icon = R.drawable.cart_icon_250952
                ),
                BottomNavItem("Orders",
                    route = Screens.Orders.route,
                    icon = R.drawable.order_number_icon_149906),
                BottomNavItem("Profile", route = Screens.Profile.route,
                    icon = R.drawable._092564_about_mobile_ui_profile_ui_user_website_114033)

            ), navcontroller =navController , onclick ={
                navController.navigate(it.route)
            } )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(product.size) { item ->
                CardView(product = product[item],
                    navController = navController,
                    productviewmodel = productviewmodel)
            }
        }
    }
}

