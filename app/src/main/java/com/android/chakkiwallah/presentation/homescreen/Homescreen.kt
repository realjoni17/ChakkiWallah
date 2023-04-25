package com.android.chakkiwallah.presentation.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.productscreen.productViewModel
import com.android.chakkiwallah.presentation.productscreen.productlist


@SuppressLint("SuspiciousIndentation", "StateFlowValueCalledInComposition",
    "UnusedMaterialScaffoldPaddingParameter"
)

@Composable
/**
fun HomeScreen(product: Product,
               viewModel: HomeScreenViewModel = hiltViewModel()) {
    val state = viewModel.getAllProducts.collectAsState()

*/
fun HomeScreen(homescreenviewModel:HomeScreenViewModel = hiltViewModel() ){
    val state = homescreenviewModel.getAllProducts.collectAsState()
    HomeScreenItem(product = state.value.product!! )
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenItem(product: List<Product>,
    homescreenviewModel:HomeScreenViewModel = hiltViewModel()) {
    val state = homescreenviewModel.getAllProducts.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ChakkiWallah") }) }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(product.size) { item ->
                MyCardView(product = product[item])
            }
        }
    }
}

@Composable
fun MyCardView(
    product: Product,
    homescreenviewModel:HomeScreenViewModel = hiltViewModel(),
    detailscreenviewmodel:productViewModel = hiltViewModel()) {
    val state = homescreenviewModel.getAllProducts.collectAsState()
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {

}