package com.android.chakkiwallah.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun CartScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()
        .wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center) {
        Text(text = "Cart Screen")
    }
}