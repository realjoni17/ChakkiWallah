package com.android.chakkiwallah.presentation.signup

data class SignUpState(
    var isLoading: Boolean = false,
    var isSignedUp: String? = null,
    val error: String? = null
)