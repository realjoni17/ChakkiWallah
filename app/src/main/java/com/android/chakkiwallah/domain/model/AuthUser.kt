package com.android.chakkiwallah.domain.model

data class AuthUser(
    val email: String = "",
    val password: String = "",
    var id: String = ""
    )


data class User(
    val name: String = "",
    val phoneNumber: String = "",
    val address : String = "",
    val profilePicUrl : String = ""
)