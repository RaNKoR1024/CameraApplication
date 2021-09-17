package com.rankor.cameraapplication.data.remote

import com.rankor.cameraapplication.data.db.camera.Camera


data class CameraResponse(
    val id: Int,
    val title: String,
    val city_id: Int,
    val city: String,
    val latitude: String,
    val longitude: String,
    val subtitle: String?,
    val group_id: Int,
    val camera_group_id: Int,
    val camera_group_title: String,
    val group_title: String,
) {

    fun parseToCamera(): Camera =
        Camera(
            id = id,
            title = title
        )
}