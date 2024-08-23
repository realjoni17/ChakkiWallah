package com.android.chakkiwallah.presentation.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Image
import com.android.chakkiwallah.R


@Composable
fun Splash() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.splash), // Use painterResource for JPG
            contentDescription = null, // Provide a meaningful description for accessibility
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center// Adjust the content scale as needed
        )
    }
}
