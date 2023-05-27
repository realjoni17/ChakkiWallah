package com.android.chakkiwallah.presentation.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.chakkiwallah.R
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.bottom_navbar.BottomNavItem
import com.android.chakkiwallah.presentation.bottom_navbar.NavBar
import com.android.chakkiwallah.presentation.homescreen.components.CardView
import com.android.chakkiwallah.presentation.homescreen.components.HomeScreenItem
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.productscreen.DetailViewModel


@SuppressLint("SuspiciousIndentation", "StateFlowValueCalledInComposition",
    "UnusedMaterialScaffoldPaddingParameter"
)


@Composable

fun HomeScreen(homescreenviewModel:HomeScreenViewModel = hiltViewModel(),
navController: NavController,productviewmodel: DetailViewModel){

    val state = homescreenviewModel.getAllProducts.collectAsState()
    HomeScreenItem(product = state.value.product!! ,
        navController = navController,
        productviewmodel =  productviewmodel)
}


