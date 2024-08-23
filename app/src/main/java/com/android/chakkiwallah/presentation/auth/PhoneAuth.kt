import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

@Composable
fun PhoneAuthScreen() {
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var verificationId by remember { mutableStateOf("") }
    var code by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }
    var isCaptchaVerified by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    // Force reCAPTCHA flow for testing
    auth.firebaseAuthSettings.forceRecaptchaFlowForTesting(true)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") }
        )

        Button(
            onClick = {
                if (!isCaptchaVerified) {
                    // Trigger reCAPTCHA verification
                    isLoading = true
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber.text)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(context as ComponentActivity)
                        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                isLoading = false
                                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "Sign-in successful")
                                        Sucess(context, "Sign-in successful")
                                    } else {
                                        // Handle sign-in failure
                                        Sucess(context, "Sign-in failed: ${task.exception?.message}")
                                    }
                                }
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                isLoading = false
                                Log.d(TAG, "onVerificationFailed: ${e.message}")
                                val errorMessage = when (e) {
                                    is FirebaseAuthInvalidCredentialsException -> "Invalid phone number format."
                                    is FirebaseTooManyRequestsException -> "Too many requests. Please try again later."
                                    is FirebaseAuthException -> "Authentication failed. Please try again."
                                    else -> "Verification failed. Please check your internet connection."
                                }
                                Sucess(context, errorMessage)
                            }

                            override fun onCodeSent(
                                id: String,
                                token: PhoneAuthProvider.ForceResendingToken
                            ) {
                                isLoading = false
                                verificationId = id
                                isCaptchaVerified = true // Set CAPTCHA verification as successful
                                Sucess(context, "OTP sent successfully!")
                            }
                        })
                        .build()

                    PhoneAuthProvider.verifyPhoneNumber(options)
                }
            },
            enabled = phoneNumber.text.isNotEmpty() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Send Verification Code")
            }
        }

        if (verificationId.isNotEmpty()) {
            TextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Verification Code") }
            )

            Button(
                onClick = {
                    val credential = PhoneAuthProvider.getCredential(verificationId, code.text)
                    auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Sign-in successful")
                            Sucess(context as ComponentActivity, "Sign-in successful")
                        } else {
                            // Handle sign-in failure
                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                Sucess(context as ComponentActivity, "Invalid verification code.")
                            } else if (task.exception is FirebaseAuthInvalidUserException) {
                                Sucess(context as ComponentActivity, "User not found.")
                            } else {
                                Sucess(context as ComponentActivity, "Sign-in failed: ${task.exception?.message}")
                            }
                        }
                    }
                },
                enabled = code.text.isNotEmpty()
            ) {
                Text("Verify Code")
            }
        }
    }
}

fun Sucess(context: ComponentActivity, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}
