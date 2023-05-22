package com.android.chakkiwallah.presentation.bottom_navbar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.chakkiwallah.R

@Composable
fun NavBar(items : List<BottomNavItem>,
    navcontroller : NavController,
onclick: (BottomNavItem) -> Unit
           )
{

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_700),
        contentColor = colorResource(id = R.color.black)
    ) {
        val navBackStackEntry by navcontroller.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach {item ->
            val selected = item.route == navBackStackEntry?.destination?.route
            BottomNavigationItem(selected = selected, onClick = {onclick(item)},
            selectedContentColor = Color.White,
                unselectedContentColor = Color.White, icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = { Text(text = item.badgeCount.toString()) }) {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = ""
                                )
                            }
                        } else {
                            Icon(painter = painterResource(id = item.icon), contentDescription = "")
                        }
                        if (selected) {
                            Text(text = item.name, textAlign = TextAlign.Center, fontSize = 10.sp)
                        }
                    }
                }
                )




        }

    }
    
}