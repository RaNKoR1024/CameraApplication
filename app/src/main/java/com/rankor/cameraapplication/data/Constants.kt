package com.rankor.cameraapplication.data

object Constants {
    const val PREVIEW_URL = "https://orionnet.online/api/v2/preview_images/"
    const val VIDEO_URL_START = "https://krkvideo1.orionnet.online/cam"
    const val VIDEO_URL_END = "/embed.html"
}

enum class LoadState{
    LOADING,
    ERROR,
    SUCCESS,
    SUCCESS_SEARCH
}