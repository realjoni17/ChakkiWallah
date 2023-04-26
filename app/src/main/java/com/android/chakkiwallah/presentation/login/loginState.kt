package com.android.chakkiwallah.presentation.login

data class loginState(
    var isLoading: Boolean = false,
    var isSignedIn: String? = null,
    val error: String? = null
)