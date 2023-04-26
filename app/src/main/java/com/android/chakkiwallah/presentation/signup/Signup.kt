package com.android.chakkiwallah.presentation.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Signup(onSignUpClick: () -> Unit, onBackToLoginClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var phoneNoError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Create an account",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { newName ->
                name = newName
            },
            label = { Text(text = "Name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phoneNo,
            onValueChange = { newPhoneNo ->
                phoneNo = newPhoneNo
                phoneNoError = newPhoneNo.length != 10
            },
            label = { Text(text = "Enter your Phone No") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            isError = phoneNoError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        if (phoneNoError) {
            Text(
                text = "Please enter a valid 10-digit phone number",
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Button(
            onClick = {
                if (name.isNotEmpty() && phoneNo.length == 10) {
                    // Handle sign up with name and phone number
                  //  onSignUpClick(name, phoneNo)
                } else {
                    if (name.isEmpty()) {
                        // Show error message for name field
                    }
                    if (phoneNo.length != 10) {
                        // Show error message for phone number field
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Sign Up")
        }
        TextButton(
            onClick = onBackToLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back to Login")
        }
    }
}

@Preview
@Composable
fun SignupPreview() {
    Signup(onSignUpClick = {  },
    onBackToLoginClick = {})
}
