package com.android.chakkiwallah.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen() {
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
            value = "",
            onValueChange = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp),
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
                onNext = { /*TODO*/ }
            )
        )
        TextField(
            value = "",
            onValueChange = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp),
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
                onDone = { /*TODO*/ }
            )
        )
        Button(
            onClick = { /*TODO*/ },
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
            onClick = { /*TODO*/ },
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
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
