package com.android.chakkiwallah.presentation.splash_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.android.chakkiwallah.presentation.navigation.Screens

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true ){
        navController.navigate(Screens.HomeScreen.route){
            popUpTo(Screens.Splash.route){
                inclusive = true
            }
        }
    }
    Splash()
}