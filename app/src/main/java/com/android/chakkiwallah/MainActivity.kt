package com.android.chakkiwallah

import NavBar
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.bottom_navbar.BottomNavItem
import com.android.chakkiwallah.presentation.login.LoginScreen
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.navigation.NavigationGraph
import com.android.chakkiwallah.presentation.navigation.Screens
import com.android.chakkiwallah.presentation.payment.PaymentScreen
import com.android.chakkiwallah.presentation.payment.PaymentViewModel
import com.android.chakkiwallah.presentation.productscreen.DetailViewModel
import com.android.chakkiwallah.presentation.ui.theme.ChakkiWallahTheme
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {

    private val productDetailViewModel = viewModels<DetailViewModel>()
    private val PAYMENT_REQUEST_CODE = 1234
    private val viewModel = viewModels<PaymentViewModel>()
    private val loginViewModel = viewModels<LoginViewModel>()
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChakkiWallahTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(bottomBar = { NavBar(items = items, navController = navController) }, topBar = {
                        Text(
                            text = "ChakkiWallah"
                        )
                    }) {

                        NavigationGraph(
                            navController = navController,
                            detailViewModel = productDetailViewModel.value
                        )

                    }


                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PAYMENT_REQUEST_CODE) {
            val result = data?.getStringExtra("response")
            if (result != null) {
                val paymentData = JSONObject(result)
                // Save payment data to Firestore
                viewModel.value.savePaymentData(paymentData)
            }
        }
    }

    override fun onPaymentSuccess(paymentId: String?) {
        val uid = loginViewModel.value.uid
        if (uid != null) {
            viewModel.value.transferCartToOrders(uid, paymentId ?: "")
        }
        Log.d("Vaani", "Payment successful: $paymentId")
        // Handle success
    }

    override fun onPaymentError(code: Int, response: String?) {
        Log.e("Payment", "Payment failed: $code, Response: $response")

        // Parse the response to check for cancellation
        val jsonResponse = response?.let { JSONObject(it) }
        val errorDescription = jsonResponse?.getJSONObject("error")?.getString("description")

        // Check if the payment was canceled
        if (errorDescription != null && errorDescription.contains("Payment processing cancelled by user")) {
            // Inform the user that the payment was canceled
            Toast.makeText(this, "Payment was canceled. Please try again.", Toast.LENGTH_LONG).show()
        } else {
            // Handle other types of errors
            Toast.makeText(this, "Payment failed: $errorDescription", Toast.LENGTH_LONG).show()
        }
        Log.e("Payment", "Payment failed: $code, Response: $response")
        // Optionally, you can provide a retry button or logic here
    }


}






val items = listOf(
    BottomNavItem(
        "Home",
        route = Screens.HomeScreen.route,
        icon = R.drawable.icons8_home
    ),
    BottomNavItem(
        "Cart",
        route = Screens.Cart.route,
        icon = R.drawable.cart_icon_250952
    ),
    BottomNavItem(
        "Orders",
        route = Screens.Orders.route,
        icon = R.drawable.order_number_icon_149906
    ),
    BottomNavItem(
        "Profile",
        route = Screens.Profile.route,
        icon = R.drawable._092564_about_mobile_ui_profile_ui_user_website_114033
    )
)



