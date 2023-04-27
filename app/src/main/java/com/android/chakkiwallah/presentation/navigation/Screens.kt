package com.android.chakkiwallah.presentation.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "login_screen")
    object Signup : Screens(route = "signup_screen")
    object Detail : Screens(route = "productdetail_Screen")
    object HomeScreen : Screens(route = "homeScreen_screen")

}
