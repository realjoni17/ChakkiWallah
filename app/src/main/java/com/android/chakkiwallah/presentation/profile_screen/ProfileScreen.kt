import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.User
import com.android.chakkiwallah.presentation.animation.LoadingScreen
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.profile_screen.UserViewModel

@Composable
fun UserProfileScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val userId = loginViewModel.uid!!
    val userDataState by userViewModel.userData.collectAsState(initial = Resource.Loading())

    LaunchedEffect(userId) {
        userViewModel.getUserData(userId) // Pass userId to the function
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when (userDataState) {
            is Resource.Loading -> {
                LoadingScreen()
            }
            is Resource.Error -> {
                Text("Error: ${(userDataState as Resource.Error).message}", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                val user = (userDataState as Resource.Success<User?>).data
                user?.let {
                    Log.d(TAG, "Joni:${it.profilePicUrl} ")
                    Text("Name: ${it.name}")
                    Text("Phone: ${it.phoneNumber}")
                    Text("Address: ${it.address}")
                    if (it.profilePicUrl.isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(it.profilePicUrl),

                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    } else {
                        Text("No Profile Picture", modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                } ?: run {
                    Text("User not found", modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screens.EditProfile.route) }) {
            Text("Edit Profile")
        }
    }
}
