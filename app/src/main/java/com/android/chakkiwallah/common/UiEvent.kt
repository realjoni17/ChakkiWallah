package com.android.chakkiwallah.common

import android.app.Activity

sealed class UiEvent {
    data class ShowSnackBar(val uiText: String) : UiEvent()
    object Initial : UiEvent()
    object NavigatePhoneNumberUi : UiEvent()
    data class PhoneNumberUiButtonClick(val activity: Activity) : UiEvent()
    object NavigateOtpUi : UiEvent()
    object VerifyOtpUiButtonClick : UiEvent()
    data class OnResendOtpClick(val activity: Activity) : UiEvent()
    object UserLoggedIn : UiEvent()
}