package com.android.chakkiwallah.presentation.profile_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/*

@Composable
fun ProfileScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "Profle Screen")
    }
}*/
@Composable
fun UserProfileScreen(
    userViewModel: UserViewModel,
    onLogoutClick: () -> Unit,
    navController: NavController
) {
    val user by userViewModel.user.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // User details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${user.name}",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "${user.email}",
                style = MaterialTheme.typography.subtitle1
            )
            // Add more user details like address, phone number, etc.
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User actions
        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
        // Add more user actions like edit profile, order history, etc.
    }
}

class UserViewModel : ViewModel() {
    private val _user = MutableStateFlow(User("John Doe", "john.doe@example.com"))
    @SuppressLint("RestrictedApi")
    val user: StateFlow<User> = _user.asStateFlow()
}

data class User(val name: String, val email: String)