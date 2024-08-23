package com.android.chakkiwallah.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.android.chakkiwallah.common.Constants.CART_PRODUCTS_FIELD
import com.android.chakkiwallah.common.Constants.USER_COLLECTION
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.domain.model.Cart
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics.Param.QUANTITY

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.google.firestore.admin.v1.Index
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
private val firebaseAuth: FirebaseAuth,
    private val firebaseRealTimeDatabase: FirebaseDatabase
) : FirebaseRepository {



    override fun firebaseSignIn(user: AuthUser): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        trySend(Resource.Loading())
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).addOnSuccessListener {
            trySend(Resource.Success("Login Successful"))
        }.addOnFailureListener {
            trySend(Resource.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }



    override suspend fun getAllProducts(): Resource<List<Product>> {
        val result: List<Product>
        return try {
            result = fireStore.collection("products").get().await().map {
                it.toObject(Product::class.java)
            }
            Resource.Success(result)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

    override fun addProductToCart(cartProduct: Product, userId: String): Flow<Resource<Task<Void>>> {
        return flow {
            emit(Resource.Loading())
            val result = fireStore.collection("user_collection").document(userId)
                .update("cartProducts", FieldValue.arrayUnion(cartProduct))
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun deleteProductFromCart(userId: String, cartProduct: Cart): Resource<Task<Void>> {
        return try {
            val result = fireStore.collection(USER_COLLECTION).document(userId)
                .update(CART_PRODUCTS_FIELD, FieldValue.arrayRemove(cartProduct))
            Resource.Success(result)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())

        }
    }

    override fun currentUserExist(): Flow<Boolean> {
        return flow {
            emit(firebaseAuth.currentUser != null)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }



    override fun userLogOut() {
        return firebaseAuth.signOut()
    }

    override fun uid(): String? {
      return  firebaseAuth.currentUser?.uid
    }


    private val userCartCollection = currentUser()?.uid?.let {
        fireStore.collection(USER_COLLECTION)
    }
    override fun getCartItems(userId: String): Flow<Resource<List<Product>>> {
        return flow {
            emit(Resource.Loading())
            val documentSnapshot = fireStore.collection("user_collection").document(userId).get().await()

            val cartProducts = documentSnapshot.get("cartProducts") as? List<Map<String, Any>> ?: emptyList()

            // Map the retrieved data to Product objects
            val products = cartProducts.map { data ->
                Product(
                    id = data["id"] as String,
                    name = data["name"] as String,
                    price = (data["price"] as Number).toDouble(),
                    quantity = (data["quantity"] as? Number)?.toInt() ?: 1
                )
            }

            emit(Resource.Success(products))
        }.catch { exception ->
            emit(Resource.Error(exception.message.toString()))
        }
    }

    override fun updateProductQuantity(userId: String, productId: String, newQuantity: Int): Flow<Resource<Void>> {
        return flow {
            emit(Resource.Loading())

            // Update the quantity in Firestore
            val result = fireStore.collection("user_collection").document(userId)
                .update("cartProducts", FieldValue.arrayRemove(Product(productId, "", 0.0, 0))) // Remove old product
                .await() // Await the completion of the previous operation

            // Add the updated product with new quantity
            val updatedProduct = Product(productId, "", 0.0, newQuantity) // Adjust fields as necessary
            fireStore.collection("user_collection").document(userId)
                .update("cartProducts", FieldValue.arrayUnion(updatedProduct)) // Add updated product
                .await()

            emit(Resource.Success(result))
        }.catch { exception ->
            emit(Resource.Error(exception.message.toString()))
        }
    }
    override fun calculateTotalPrice(products: List<Product>): Double {
        return products.sumOf { it.price * it.quantity }
    }

   override fun savePaymentData( paymentData: JSONObject, userId: String) {
       fireStore.collection("user_collection").document(userId).collection("payments").add(paymentData)
            .addOnSuccessListener {  Log.d("Vaani", "Payment data saved successfully: $paymentData") }
            .addOnFailureListener {  exception ->
                Log.e("Vaani", "Error saving payment data: ${exception.message}", exception) }
    }

    override fun getUserDetails(userId: String): Flow<Resource<AuthUser>> = flow {
        val documentSnapshot = fireStore
            .collection(USER_COLLECTION)
            .document(userId)
            .get()
            .await() // Use Kotlin Coroutines to await the result

        if (documentSnapshot.exists()) {
            // Log the document data for debugging
            val userData = documentSnapshot.data
            Log.d("FirestoreData", "User data: $userData")

            // Convert to AuthUser object
            val user = documentSnapshot.toObject(AuthUser::class.java)

            // Check if user is null and log the result
            if (user != null) {
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("Failed to convert document to AuthUser"))
            }
        } else {
            emit(Resource.Error("User not found"))
        }
    }.catch { exception ->
        emit(Resource.Error("Failed to fetch user details: ${exception.message}"))
    }


    override fun firebaseSignUp(user: AuthUser): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())

        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener { authResult ->
                val userUid = authResult.user?.uid ?: return@addOnSuccessListener

                // Store user details in Firestore
                fireStore.collection(USER_COLLECTION).document(userUid).set(user)
                    .addOnSuccessListener {
                        trySend(Resource.Success("Signup Successful"))

                        // Fetch user details after signup
                        launch { // Start a coroutine to collect the flow
                            getUserDetails(userUid).collect { userResource ->
                                when (userResource) {
                                    is Resource.Success -> {
                                        // Handle the user details here if needed
                                        println("User Details: ${userResource.data}")
                                        Log.d(TAG, "Vanni:${userResource.data} ")
                                    }
                                    is Resource.Error -> {
                                        println("Error fetching user details: ${userResource.message}")
                                    }
                                    is Resource.Loading -> {
                                        // Optionally handle loading state
                                    }
                                }
                            }
                        }
                    }
                    .addOnFailureListener {
                        trySend(Resource.Error(it.message.toString()))
                    }
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.message.toString()))
            }

        awaitClose { close() }
    }


}







