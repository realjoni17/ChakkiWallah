package com.android.chakkiwallah.presentation.profile_screen


import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.chakkiwallah.common.Resource

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.InputStream
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.chakkiwallah.domain.model.AuthUser

import androidx.activity.result.launch
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.chakkiwallah.domain.model.User
import com.android.chakkiwallah.presentation.login.LoginViewModel
import kotlinx.coroutines.launch



@Composable
fun InsertUser(
    onDismiss: () -> Unit,
    userViewModel: UserViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var loading by remember { mutableStateOf(false) }
    val uid = loginViewModel.uid ?: ""
    val context = LocalContext.current
    val userDataState = userViewModel.userData.collectAsState()
    val scope = rememberCoroutineScope()
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Insert User Data") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                imageUri?.let {
                    val bitmap = it.toBitmap(context.contentResolver)
                    bitmap?.let { bmp ->
                        Image(
                            bitmap = bmp.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }

                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("Select Image")
                }

                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
                TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })

                when (userDataState.value) {
                    is Resource.Loading -> CircularProgressIndicator()
                    is Resource.Error -> {
                        Text(text = "Error: ${(userDataState.value as Resource.Error).message}", color = MaterialTheme.colors.error)
                    }

                    is Resource.Success -> {
                        val user = (userDataState.value as Resource.Success<User?>).data
                        // Handle the success case, e.g., show user data
                        user?.let {
                            Text("User saved successfully: ${it.name}")
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                loading = true
                val registeredUid = uid

                // Validate inputs
                if (name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || imageUri == null) {
                    Toast.makeText(context, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
                    loading = false
                    return@Button
                }

                // Upload image and save user data

                scope.launch {
                    try {
                        val imageUrl = userViewModel.uploadProfilePic(imageUri!!, registeredUid) // Ensure imageUri is not null
                        val authUser = imageUrl?.let {
                            User(
                                name = name,
                                phoneNumber = phoneNumber,
                                address = address,
                                profilePicUrl = it
                            )
                        }

                        if (authUser != null) {
                            userViewModel.saveUserData(authUser, registeredUid)
                        }
                        Toast.makeText(context, "User saved successfully", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    } finally {
                        loading = false
                        onDismiss() // Dismiss dialog after completion
                    }
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

// Extension function to convert Uri to Bitmap
fun Uri.toBitmap(contentResolver: ContentResolver): Bitmap? {
    return try {
        val inputStream = contentResolver.openInputStream(this)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


