package com.rankor.cameraapplication.data.db.camera

import androidx.room.*


@Dao
interface CameraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCamera(camera: Camera)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCamera(camera: List<Camera>)

    @Update
    suspend fun updateCamera(camera: Camera)

    @Delete
    suspend fun deleteCamera(camera: Camera)

    @Query("SELECT * FROM Camera ORDER BY id ASC")
    suspend fun getAllCameras(): List<Camera>

    @Query("DELETE FROM Camera")
    suspend fun clearCameras()
}