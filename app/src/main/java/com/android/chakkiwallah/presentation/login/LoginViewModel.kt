package com.android.chakkiwallah.presentation.login

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
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _signInState = Channel<loginState>()
    val signInState = _signInState.receiveAsFlow()

    val currentUserExist = firebaseRepository.currentUserExist()
    val uid = firebaseRepository.uid()

    fun loginUser(user: AuthUser) = viewModelScope.launch {
        firebaseRepository.firebaseSignIn(user).collect {result ->
            when(result){
                is Resource.Loading -> {
                    _signInState.send(loginState(isLoading = true))
                }
                is Resource.Success -> {
                    _signInState.send(loginState(isSignedIn = "Signed In Successful"))

                }
                is Resource.Error -> {
                    _signInState.send(loginState(error = result.message))
                }
            }

        }
    }


}
