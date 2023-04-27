package com.android.chakkiwallah.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.presentation.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(loginInViewModel: LoginViewModel = hiltViewModel(),
navController: NavController) {
    val loginstate = loginInViewModel.signInState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isUserExist = loginInViewModel.currentUserExist.collectAsState(initial = true)
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }

    /**
    LaunchedEffect(key1 = Unit) {
    if (isUserExist.value) {
    }
    }

    if (!isUserExist.value) {*/
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .paddingFromBaseline(top = 184.dp, bottom = 32.dp)
                .align(Alignment.CenterHorizontally)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp)
                .focusRequester(focusRequester1),
            placeholder = { Text("Email address") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                textColor = MaterialTheme.colors.onBackground
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequester2.requestFocus() }
            )
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp)
                .focusRequester(focusRequester2),
            placeholder = { Text("Password") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                textColor = MaterialTheme.colors.onBackground
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusRequester2.freeFocus()
                }
            )
        )
        Button(
            onClick = {
                scope.launch(Dispatchers.Main) {
                    loginInViewModel.loginUser(
                        AuthUser(
                            email, password
                        )
                    )
                }
                navController.navigate(Screens.HomeScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onPrimary
            )
        }
        TextButton(
            onClick = {  navController.navigate(Screens.Signup.route)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp)
        ) {
            Text(
                text = "Don't have an account? Sign up",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
        }
    }

    LaunchedEffect(key1 = loginstate.value?.isSignedIn) {
        scope.launch {
            if (loginstate.value?.isSignedIn?.isNotEmpty() == true) {
                val success = loginstate.value?.isSignedIn
                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
            }
        }
    }
    LaunchedEffect(key1 = loginstate.value?.error) {
        scope.launch {
            if (loginstate.value?.error?.isNotBlank() == true) {
                val error = loginstate.value?.error
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }
}


/**
@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
 */
