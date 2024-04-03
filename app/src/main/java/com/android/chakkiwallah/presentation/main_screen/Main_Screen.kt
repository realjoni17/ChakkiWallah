package com.android.chakkiwallah.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.chakkiwallah.R
import com.android.chakkiwallah.presentation.bottom_navbar.BottomNavItem
import com.android.chakkiwallah.presentation.bottom_navbar.NavBar
import com.android.chakkiwallah.presentation.homescreen.HomeScreen
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.productscreen.DetailViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun  MainScreen(navController : NavController ,
viewmodel : DetailViewModel) {
val nav = rememberNavController()
    Scaffold(bottomBar = { NavBar(items = listOf(
        BottomNavItem(name = "Home",
        route = Screens.HomeScreen.route,
        icon = R.drawable.icons8_home,
        badgeCount = 0),
        BottomNavItem(name = "Cart",
            route = Screens.Cart.route,
            icon = R.drawable.cart_icon_250952,
            badgeCount = 1
        ),
        BottomNavItem(name = "Orders",
        route = Screens.Orders.route,
        icon = R.drawable.order_number_icon_149906,
        badgeCount = 2),
        BottomNavItem(name = "Profile",
        icon = R.drawable._092564_about_mobile_ui_profile_ui_user_website_114033,
        route = Screens.Profile.route, badgeCount = 3)
    ),
        navcontroller =navController ,
        onclick ={navController.navigate(it.route)} )}) {
        HomeScreen(navController = navController, productviewmodel =viewmodel )
    }

}
/*
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
},
content = HomeScreen(navController = navController, productviewmodel = viewmodel )
)*/
