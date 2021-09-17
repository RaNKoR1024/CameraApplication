package com.rankor.cameraapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rankor.cameraapplication.data.db.camera.CameraDao
import com.rankor.cameraapplication.data.db.camera.Camera


@Database(
    entities = [Camera::class],
    version = 1,
    exportSchema = false
)
abstract class CameraDatabase : RoomDatabase() {
    abstract fun cameraDao(): CameraDao

    companion object {
        @Volatile
        private var INSTANCE: CameraDatabase? = null

        fun getDatabase(context: Context): CameraDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CameraDatabase::class.java,
                    "camera_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}