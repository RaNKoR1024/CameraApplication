package com.rankor.cameraapplication.data.db.camera

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Camera(
    @PrimaryKey
    @ColumnInfo(index = true) var id: Int,
    var title: String,
)

