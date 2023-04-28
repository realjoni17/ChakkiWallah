package com.android.chakkiwallah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.android.chakkiwallah.presentation.login.LoginScreen
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.navigation.NavigationGraph
import com.android.chakkiwallah.presentation.productscreen.DetailViewModel
import com.android.chakkiwallah.presentation.ui.theme.ChakkiWallahTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val productDetailViewModel = viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChakkiWallahTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
              NavigationGraph(navController = navController, detailViewModel = productDetailViewModel.value)

                }
            }
        }
    }
}

