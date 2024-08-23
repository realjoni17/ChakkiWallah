import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.domain.model.User
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.profile_screen.InsertUser
import com.android.chakkiwallah.presentation.profile_screen.UserViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun UserProfileScreen(userViewModel: UserViewModel = hiltViewModel(),
                      loginViewModel: LoginViewModel= hiltViewModel(),
                      navController: NavController) {
    val userId: String  = loginViewModel.uid!!
    val userData by userViewModel.userData.collectAsState()

    LaunchedEffect(userId) {
        userViewModel.getUserData(userId)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when (userData) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Success -> {
                val authUser = (userData as Resource.Success<User?>).data
                if (authUser != null) {
                    Text("Welcome, ${authUser.name}")
                  //  data.value.data?.let { Text(text = it.profilePicUrl) }
                } else {
                    Text("User not found")
                }
            }

            is Resource.Error -> {
                Text("Error: ${(userData as Resource.Error).message}")
            }
        }

        var showDialog by remember { mutableStateOf(false) }
        Button(onClick = { showDialog = true }) {
            Text("Insert User Data")
        }

        if (showDialog) {
            InsertUser(onDismiss = { showDialog = false })
        }
    }
    }

