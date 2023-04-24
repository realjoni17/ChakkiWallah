package com.android.chakkiwallah.presentation.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeScreenViewModel@Inject constructor(
    private val firebaseRepository: FirebaseRepository
) :ViewModel() {
    private val _getAllProducts: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState())
    val getAllProducts: StateFlow<HomeScreenState> = _getAllProducts

    init {
        getAllProducts()
    }

    private fun getAllProducts() = viewModelScope.launch {
        firebaseRepository.getAllProducts().let { result ->
            when (result) {
                is Resource.Success -> {
                    _getAllProducts.value = HomeScreenState(product = result.data)
                }
                is Resource.Loading -> {
                    _getAllProducts.value = HomeScreenState(isLoading = true)
                }
                is Resource.Error -> {
                    _getAllProducts.value = HomeScreenState(error = result.message)
                }
            }
        }
    }
}