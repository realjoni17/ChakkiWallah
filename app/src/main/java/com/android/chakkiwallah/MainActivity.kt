package com.android.chakkiwallah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.homescreen.HomeScreen
import com.android.chakkiwallah.presentation.login.LoginScreen
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.productscreen.productscreen
import com.android.chakkiwallah.presentation.signup.SignUpScreen
import com.android.chakkiwallah.presentation.ui.theme.ChakkiWallahTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel = viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChakkiWallahTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
              LoginScreen()
                }
            }
        }
    }
}

