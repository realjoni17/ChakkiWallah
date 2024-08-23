import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(loginInViewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    val loginState = loginInViewModel.signInState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isUserExist = loginInViewModel.currentUserExist.collectAsState(initial = true)
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (isUserExist.value) {
            navController.navigate(Screens.HomeScreen.route)
        }
    }

    if (!isUserExist.value) {
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
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = Color.Transparent,
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
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = if (passwordVisibility) Icons.Default.ThumbUp else Icons.Default.Close, contentDescription = null)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = Color.Transparent,
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
                    isLoading = true
                    scope.launch(Dispatchers.Main) {
                        loginInViewModel.loginUser(AuthUser(email, password))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(top = 16.dp, bottom = 8.dp),
                enabled = !isLoading
            ) {
                Text(
                    text = if (isLoading) "Logging in..." else "Log in",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary
                )
            }

            TextButton(
                onClick = {
                    navController.navigate(Screens.Signup.route)
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

        LaunchedEffect(loginState.value?.isSignedIn) {
            loginState.value?.isSignedIn?.let { isSignedIn ->
                if (isSignedIn.isNotEmpty()) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                    navController.navigate(Screens.HomeScreen.route)
                }
            }
        }

        LaunchedEffect(loginState.value?.error) {
            loginState.value?.error?.let { error ->
                if (error.isNotBlank()) {
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
