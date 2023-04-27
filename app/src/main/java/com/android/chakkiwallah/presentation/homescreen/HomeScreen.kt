package com.android.chakkiwallah.presentation.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.productscreen.productViewModel


@SuppressLint("SuspiciousIndentation", "StateFlowValueCalledInComposition",
    "UnusedMaterialScaffoldPaddingParameter"
)


@Composable
/**
fun HomeScreen(product: Product,
               viewModel: HomeScreenViewModel = hiltViewModel()) {
    val state = viewModel.getAllProducts.collectAsState()

*/
fun HomeScreen(homescreenviewModel:HomeScreenViewModel = hiltViewModel(),
navController: NavController){

    val state = homescreenviewModel.getAllProducts.collectAsState()
    HomeScreenItem(product = state.value.product!! , navController = navController )
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenItem(product: List<Product>,
    homescreenviewModel:HomeScreenViewModel = hiltViewModel(),
navController: NavController
)
{
    val state = homescreenviewModel.getAllProducts.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ChakkiWallah") }) }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(product.size) { item ->
                MyCardView(product = product[item], navController = navController)
            }
        }
    }
}

@Composable
fun MyCardView(
    product: Product,
    homescreenviewModel:HomeScreenViewModel = hiltViewModel(),
    productviewmodel:productViewModel = hiltViewModel(),
navController: NavController) {
    val state = homescreenviewModel.getAllProducts.collectAsState()
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                productviewmodel.setProduct(product)
                navController.navigate(Screens.Detail.route)
            },
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

/**
@Preview
@Composable
fun HomeScreenPreview() {

}
 */