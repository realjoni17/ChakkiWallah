package com.android.chakkiwallah.presentation.signup

import android.widget.Toast
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.chakkiwallah.domain.model.AuthUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SignUp(signupviewmodel:SignupViewModel = hiltViewModel(),
           navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isPasswordConfirmationValid by remember { mutableStateOf(false) }
    val focusRequesterPassword = remember { FocusRequester() }
    val focusRequesterPasswordConfirmation = remember { FocusRequester() }
    val state = signupviewmodel.signUpState.collectAsState(initial = null)

    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterPassword.requestFocus() }
            ),
            isError = !isEmailValid
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterPasswordConfirmation.requestFocus() }
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = !isPasswordValid
        )
        /**
        TextField(
            value = passwordConfirmation,
            onValueChange = { passwordConfirmation = it },
            label = { Text("Confirm password") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {  }
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = !isPasswordConfirmationValid,
            modifier = Modifier
                .focusRequester(focusRequesterPasswordConfirmation)
                .onFocusChanged {
                    if (it.isFocused) {
                        isPasswordConfirmationValid = passwordConfirmation == password
                    }
                }
        )
        */
        Button(
            onClick =  { scope.launch(Dispatchers.Main) {
                signupviewmodel.createUser(
                    AuthUser(
                        email, password
                    )
                )
            }
                       }  ,
         //   enabled = isEmailValid && isPasswordValid && isPasswordConfirmationValid
        ) {
            Text("Sign up")
        }
    }

    /**
    fun submit() {
        isEmailValid = email.isValidEmail()
        isPasswordValid = password.isValidPassword()
        isPasswordConfirmationValid = passwordConfirmation == password

        if (isEmailValid && isPasswordValid && isPasswordConfirmationValid) {

        }
    }
    */
    LaunchedEffect(key1 = state.value?.isSignedUp) {
        scope.launch {
            if (state.value?.isSignedUp?.isNotEmpty() == true) {
                val success = state.value?.isSignedUp
                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
            }
        }
    }
    LaunchedEffect(key1 = state.value?.error) {
        scope.launch {
            if (state.value?.error?.isNotBlank() == true) {
                val error = state.value?.error
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }
}


/**
fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(this)
}

fun String.isValidPassword(): Boolean {
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
    return passwordRegex.matches(this)
}
 */
