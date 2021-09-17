package com.rankor.cameraapplication.ui.views.listcameras

import com.rankor.cameraapplication.data.Constants.PREVIEW_URL
import com.rankor.cameraapplication.data.db.camera.Camera

class CameraItem (val camera: Camera) {
    val url: String = PREVIEW_URL + camera.id
}
