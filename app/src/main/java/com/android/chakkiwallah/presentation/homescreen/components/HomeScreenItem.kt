import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.android.chakkiwallah.R
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.bottom_navbar.BottomNavItem
import com.android.chakkiwallah.presentation.homescreen.components.CardView
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.productscreen.DetailViewModel

@Composable
fun HomeScreenView(
    products: List<Product>,
    navController: NavController,
    detailViewModel: DetailViewModel
) {


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {
            items(products) { product ->
                CardView(
                    product = product,
                    navController = navController,
                    productviewmodel = detailViewModel
                )
            }
        }
    }
