package com.rankor.cameraapplication.ui.events

import com.rankor.cameraapplication.data.db.camera.Camera

// class for intents which send by views on user actions
sealed class UiIntent {

    object GetCameras : UiIntent()

    object SearchCameras : UiIntent() {
        var query = ""
    }

    object GoToCamera : UiIntent() {
        lateinit var camera: Camera
    }

}
