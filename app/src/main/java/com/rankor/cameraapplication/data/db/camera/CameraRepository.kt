package com.rankor.cameraapplication.data.db.camera

class CameraRepository(private val cameraDao: CameraDao) {

    suspend fun getAllCameras(): List<Camera> = cameraDao.getAllCameras()

    suspend fun updateCameras(cameraList: List<Camera>) {
        cameraDao.clearCameras()
        cameraDao.addCamera(cameraList)
    }
}