package com.android.chakkiwallah.presentation.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "login_screen")
    object Signup : Screens(route = "signup_screen")
    object Detail : Screens(route = "productdetail_Screen")
    object HomeScreen : Screens(route = "homeScreen_screen")

    object Cart : Screens("cart_screen")

    object Orders : Screens("order_screen")

    object Profile : Screens("profile_screen")

    object Splash : Screens("spalsh_screen")

}
