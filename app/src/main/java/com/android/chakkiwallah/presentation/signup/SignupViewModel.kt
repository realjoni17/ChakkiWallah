package com.android.chakkiwallah.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect




@HiltViewModel
class SignupViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()

    private val _signupStatus = MutableStateFlow<Resource<String>>(Resource.Loading())
    val signupStatus: StateFlow<Resource<String>> get() = _signupStatus

    private val _userDetails = MutableStateFlow<Resource<AuthUser>>(Resource.Loading())
    val userDetails: StateFlow<Resource<AuthUser>> get() = _userDetails

    fun createUser(user: AuthUser) = viewModelScope.launch {
        firebaseRepository.firebaseSignUp(user).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _signUpState.send(SignUpState(isLoading = true))
                }

                is Resource.Success -> {
                    _signUpState.send(SignUpState(isSignedUp = "Signed In Successful"))
                }

                is Resource.Error -> {
                    _signUpState.send(SignUpState(error = result.message))
                }
            }
        }
    }




    // Function to sign up the user
    fun firebaseSignUp(user: AuthUser) {
        viewModelScope.launch {
            firebaseRepository.firebaseSignUp(user).collect { resource ->
                _signupStatus.value = resource

                // If signup is successful, fetch user details
                if (resource is Resource.Success<*>) {
                    fetchUserDetails(user.id) // Assuming user.id is set after signup
                }
            }
        }
    }

    // Function to fetch user details
    private fun fetchUserDetails(userId: String) {
        viewModelScope.launch {
            firebaseRepository.getUserDetails(userId).collect { resource ->
                _userDetails.value = resource
            }
        }
    }
}
