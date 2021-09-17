package com.rankor.cameraapplication.ui.events

import com.rankor.cameraapplication.data.LoadState
import com.rankor.cameraapplication.ui.views.listcameras.CameraItem

// class for changing state of fragments
sealed class UiState {

    class CameraListState(
        val cameraList: List<CameraItem> = emptyList(),
        val loadState: LoadState = LoadState.LOADING
    ) : UiState()

    class CameraState(
        val title: String,
        val url: String
    ) : UiState()

}

