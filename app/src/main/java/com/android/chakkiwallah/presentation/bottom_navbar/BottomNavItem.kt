package com.android.chakkiwallah.presentation.bottom_navbar

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Int,
    val badgeCount: Int = 0
)
