package com.android.chakkiwallah.presentation.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


//val state = viewModel.getAllProducts.value
@SuppressLint("SuspiciousIndentation", "StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val state = viewModel.getAllProducts.collectAsState()

        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)) {
            run {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${state.value.product!!}",
                                style = MaterialTheme.typography.h2,
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                }
            }
        }



@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}