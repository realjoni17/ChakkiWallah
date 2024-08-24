import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.User
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.profile_screen.UserViewModel
import kotlinx.coroutines.launch
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.InputStream


@Composable
fun EditProfile(
    loginViewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val userId: String = loginViewModel.uid!!
    val userDataState = userViewModel.userData.collectAsState()
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    LaunchedEffect(userId) {
        userViewModel.getUserData(userId)
    }

    when (userDataState.value) {
        is Resource.Loading -> {
            CircularProgressIndicator()
        }
        is Resource.Error -> {
            Text("Error: ${(userDataState.value as Resource.Error).message}")
        }
        is Resource.Success -> {
            val user = (userDataState.value as Resource.Success<User?>).data
            user?.let {
                name = it.name
                phoneNumber = it.phoneNumber
                address = it.address
                imageUri = Uri.parse(it.profilePicUrl) // Assuming profilePicUrl is a valid URI
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Edit Profile", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") }
        )
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } ?: run {
            Text("No Profile Picture")
        }

        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Select Profile Picture")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            loading = true
            scope.launch {
                val imageUrl = uploadImageToFirebase(imageUri,context,userId) // Upload the image and get the URL
                val updatedUser = imageUrl?.let { User(name, phoneNumber, address, it) }
                if (updatedUser != null) {
                    userViewModel.saveUserData(updatedUser, userId).collect { resource ->
                        loading = false
                        when (resource) {
                            is Resource.Success -> {
                                // Handle successful update, e.g., navigate back or show a message
                            }

                            is Resource.Error -> {
                                // Handle error, e.g., show a message
                            }

                            is Resource.Loading -> {
                                // Show loading state if needed
                            }
                        }
                    }
                }
            }
        }) {
            Text("Save Changes")
        }

        if (loading) {
            CircularProgressIndicator()
        }
    }
}

// Function to upload the image to Firebase Storage
suspend fun uploadImageToFirebase(imageUri: Uri?, context: Context, userId : String): String? {
    if (imageUri == null) return null
   // val context = LocalContext.current.applicationContext
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

     // Replace with actual user ID or unique identifier
    val imageRef = storageRef.child("profile_pictures/$userId.jpg") // Define the path in storage

    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        inputStream?.let {
            val uploadTask = imageRef.putStream(it).await() // Use Kotlin Coroutines to await the upload
            val downloadUrl = imageRef.downloadUrl.await() // Get the download URL
            downloadUrl.toString() // Return the URL as a string
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null // Handle error appropriately
    }
}
