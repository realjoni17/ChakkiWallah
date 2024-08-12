import androidx.compose.foundation.layout.Column
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.text.color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.chakkiwallah.R
import com.android.chakkiwallah.presentation.bottom_navbar.BottomNavItem

@Composable
fun NavBar(items: List<BottomNavItem>, navController: NavController) {
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.purple_700),
        contentColor = colorResource(id = R.color.black)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val selected = item.route == currentRoute
            BottomNavigationItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(
                                    text = item.badgeCount.toString()
                                )
                            }) {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.name // Use item name for content description
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.name // Use item name for content description
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}