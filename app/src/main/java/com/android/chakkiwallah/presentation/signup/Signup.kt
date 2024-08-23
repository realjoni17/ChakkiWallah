package com.android.chakkiwallah.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.chakkiwallah.R
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.presentation.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUp(signupViewModel: SignupViewModel = hiltViewModel(), navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isPasswordConfirmationValid by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }

    val focusRequesterPassword = remember { FocusRequester() }
    val focusRequesterPasswordConfirmation = remember { FocusRequester() }
    val state = signupViewModel.signUpState.collectAsState(initial = null)

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.undraw_sign_up_n6im),
            contentDescription = "",
            alignment = Alignment.Center
        )

        TextField(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterPassword.requestFocus() }
            ),
            isError = !isEmailValid,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordValid = password.length >= 6 // Example validation
            },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterPasswordConfirmation.requestFocus() }
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = !isPasswordValid,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = passwordConfirmation,
            onValueChange = {
                passwordConfirmation = it
                isPasswordConfirmationValid = passwordConfirmation == password
            },
            label = { Text("Confirm Password") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = !isPasswordConfirmationValid,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (isEmailValid && isPasswordValid && isPasswordConfirmationValid) {
                    isLoading = true
                    scope.launch(Dispatchers.Main) {
                        signupViewModel.createUser(AuthUser(email, password))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Signing up..." else "Sign up")
        }

        TextButton(
            onClick = { navController.navigate(Screens.LoginScreen.route) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Already have an account? Login")
        }
    }

    LaunchedEffect(state.value?.isSignedUp) {
        if (state.value?.isSignedUp?.isNotEmpty() == true) {
            Toast.makeText(context, "Sign-up successful", Toast.LENGTH_LONG).show()
            navController.navigate(Screens.LoginScreen.route) // Navigate after successful sign-up
        }
    }

    LaunchedEffect(state.value?.error) {
        state.value?.error?.let { error ->
            if (error.isNotBlank()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }
}
