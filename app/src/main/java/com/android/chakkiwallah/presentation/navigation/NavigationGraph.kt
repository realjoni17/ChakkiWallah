package com.android.chakkiwallah.presentation.navigation

import EditProfile
import LoginScreen
import UserProfileScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.chakkiwallah.presentation.cart.CartScreen

import com.android.chakkiwallah.presentation.homescreen.HomeScreen

import com.android.chakkiwallah.presentation.order_screen.OrderListScreen
import com.android.chakkiwallah.presentation.order_screen.OrderViewModel
import com.android.chakkiwallah.presentation.productscreen.Detail
import com.android.chakkiwallah.presentation.productscreen.DetailViewModel



import com.android.chakkiwallah.presentation.signup.SignUp
import com.android.chakkiwallah.presentation.splash_screen.SplashScreen


@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    detailViewModel: DetailViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navController)

        }
        composable(route = Screens.Signup.route) {
            SignUp(navController = navController)

        }
        composable(route = Screens.HomeScreen.route){
            HomeScreen(navController = navController, productviewmodel = detailViewModel)
        }
        composable(route = Screens.Detail.route){
            Log.d("Args", it.arguments?.getString(it.toString()).toString())
            Detail(navController = navController, viewModel = detailViewModel)
        }
        composable(route = Screens.Cart.route){
            CartScreen(  navController = navController)
        }
        composable(route = Screens.Orders.route){
           OrderListScreen()
        }
        composable(route = Screens.Profile.route){
            UserProfileScreen( navController = navController
                )
        }
        composable(route = Screens.Splash.route){
            SplashScreen(navController = navController)
        }
        composable(route = Screens.EditProfile.route) {
            EditProfile()
        }

    }

}