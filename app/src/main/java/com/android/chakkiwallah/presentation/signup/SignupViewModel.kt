package com.android.chakkiwallah.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()


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
}