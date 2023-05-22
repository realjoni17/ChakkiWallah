package com.android.chakkiwallah.presentation.bottom_navbar

import com.android.chakkiwallah.R

sealed class BottomBarScreen(val title : String , val icon : Int , val route : String)
{
    object Home : BottomBarScreen("Home", R.drawable.home,"home")
    object Cart : BottomBarScreen("Cart",R.drawable.cart_icon_250952,"cart")
    object Orders : BottomBarScreen("Orders",R.drawable.order_number_icon_149906,"orders")
    object Profile : BottomBarScreen("Profile",
        R.drawable._092564_about_mobile_ui_profile_ui_user_website_114033,"profile")
}
