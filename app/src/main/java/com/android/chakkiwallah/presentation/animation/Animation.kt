package com.android.chakkiwallah.presentation.animation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.chakkiwallah.R

@Composable
fun LoadingScreen() {
    // Load the Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading)) // Change to your animation file
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever // Loop forever
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(composition, progress)
    }
}

@Composable
fun Sucess(onDismiss: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.tick))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1
    )

    LaunchedEffect(progress) {
        if (progress >= 0.99f) {
            println("Animation complete, calling onDismiss")
            onDismiss()
        } else {
            println("Progress not yet complete: $progress")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(composition, progress)
    }
}
