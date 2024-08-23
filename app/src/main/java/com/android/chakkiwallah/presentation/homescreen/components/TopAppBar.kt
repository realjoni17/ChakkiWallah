package com.android.chakkiwallah.presentation.homescreen.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyTopBar() {
    TopAppBar(
        title = { Text("ChakkiWallah") },
        actions = {
            IconButton(onClick = { /* Handle click */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More options")
            }
        }
    )
}


