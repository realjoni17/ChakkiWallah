package com.android.chakkiwallah.presentation.profile_screen



import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val fireStore: FirebaseFirestore) : ViewModel() {

    private val _userData = MutableStateFlow<Resource<User?>>(Resource.Loading())
    val userData: StateFlow<Resource<User?>> = _userData.asStateFlow()

    fun saveUserData(authUser: User, userId: String): Flow<Resource<Void?>> {
        return flow {
            emit(Resource.Loading())
            try {
                Log.d("UserViewModel", "Saving User: $authUser")
                fireStore.collection("user").document(userId).set(authUser).await()
                emit(Resource.Success(null))
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error saving user data", e)
                emit(Resource.Error(e.message.toString()))
            }
        }
    }


    fun getUserData(userId: String) {
        viewModelScope.launch {
            _userData.emit(Resource.Loading())
            try {
                val documentSnapshot = fireStore.collection("user").document(userId).get().await()
                if (documentSnapshot.exists()) {
                    val authUser = documentSnapshot.toObject<User>()
                    _userData.emit(Resource.Success(authUser))
                } else {
                    _userData.emit(Resource.Success(null)) // User not found
                }
            } catch (e: Exception) {
                _userData.emit(Resource.Error(e.message.toString()))
            }
        }
    }

    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference

    suspend fun uploadProfilePic(uri: Uri, userId: String): String? {
        return try {
            val fileRef = storageRef.child("profile_pics/$userId.jpg") // Adjust path as needed
            fileRef.putFile(uri).await() // Upload the file
            fileRef.downloadUrl.await().toString() // Get the download URL
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
