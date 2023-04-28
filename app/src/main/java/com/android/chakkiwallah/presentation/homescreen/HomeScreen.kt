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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.chakkiwallah.domain.model.Product
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



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenItem(product: List<Product>,
                   navController: NavController,
                   productviewmodel: DetailViewModel
)
{

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ChakkiWallah") }) }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(product.size) { item ->
                MyCardView(product = product[item],
                    navController = navController,
                    productviewmodel = productviewmodel)
            }
        }
    }
}

@Composable
fun MyCardView(
    product: Product,
    productviewmodel:DetailViewModel = hiltViewModel(),
    navController: NavController) {

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
        ) {Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(100.dp)
                .width(100.dp),
            painter = rememberAsyncImagePainter(
                model = product.image,
                contentScale = ContentScale.Crop
            ),
            contentDescription = "Coffee"
        )
            Text(
                text = product.name,
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
            Text(
                text = product.price,
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
        }
    }
}
