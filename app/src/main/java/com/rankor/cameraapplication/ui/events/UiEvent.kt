package com.rankor.cameraapplication.ui.events

import androidx.annotation.StringRes

// class for single events like alerts, toasts etc.
sealed class UiEvent {

    class ShortToast(@StringRes val messageId: Int) : UiEvent()

}
