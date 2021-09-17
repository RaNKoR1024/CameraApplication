package com.rankor.cameraapplication.data.remote

import retrofit2.http.GET

interface OrionApi {

    @GET("v1/cameras/public")
    suspend fun getCamerasList(): List<CameraResponse>
}