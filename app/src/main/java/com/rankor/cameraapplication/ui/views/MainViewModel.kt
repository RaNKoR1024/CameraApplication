package com.rankor.cameraapplication.ui.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rankor.cameraapplication.App
import com.rankor.cameraapplication.R
import com.rankor.cameraapplication.data.Constants.VIDEO_URL_END
import com.rankor.cameraapplication.data.Constants.VIDEO_URL_START
import com.rankor.cameraapplication.data.LoadState
import com.rankor.cameraapplication.data.db.CameraDatabase
import com.rankor.cameraapplication.data.db.camera.Camera
import com.rankor.cameraapplication.data.db.camera.CameraRepository
import com.rankor.cameraapplication.ui.events.UiEvent
import com.rankor.cameraapplication.ui.events.UiIntent
import com.rankor.cameraapplication.ui.events.UiState
import com.rankor.cameraapplication.ui.views.listcameras.CameraItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as App
    private val cameraRepository = CameraRepository(CameraDatabase.getDatabase(app).cameraDao())

    private val intentChannel = Channel<UiIntent>(Channel.UNLIMITED)
    private var _state = MutableLiveData<UiState>()
    val state: LiveData<UiState>
        get() = _state
    private val _singleEvent = Channel<UiEvent>(3)
    val singleEvent: Channel<UiEvent>
        get() = _singleEvent

    private var cameraList: List<Camera> = emptyList()

    init {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                withContext(Dispatchers.Default) { reduce(it) }
            }
        }
    }

    // to simplify sending intent from view
    fun dispatch(intent: UiIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            intentChannel.send(intent)
        }
    }

    // precess received intent
    private suspend fun reduce(intent: UiIntent) {
        when (intent) {
            is UiIntent.GetCameras -> getCameras()
            is UiIntent.SearchCameras -> searchCameras(intent)
            is UiIntent.GoToCamera -> goToCamera(intent.camera)
        }
    }

    private suspend fun getCameras() {
        if (cameraList.isNotEmpty()) {
            _state.postValue(
                UiState.CameraListState(
                    loadState = LoadState.SUCCESS,
                    cameraList = List(cameraList.size) { CameraItem(cameraList[it]) }
                )
            )
            return
        }
        kotlin.runCatching {
            _state.postValue(UiState.CameraListState(loadState = LoadState.LOADING))
            val cameras = app.orionApi.getCamerasList()
            cameraList = List(cameras.size) { cameras[it].parseToCamera() }
            _state.postValue(
                UiState.CameraListState(
                    loadState = LoadState.SUCCESS,
                    cameraList = List(cameraList.size) { CameraItem(cameraList[it]) }
                )
            )
            cameraRepository.updateCameras(cameraList)
        }.onFailure {
            val cameras = cameraRepository.getAllCameras()
            if (cameras.isEmpty()) {
                _state.postValue(
                    UiState.CameraListState(
                        loadState = LoadState.ERROR
                    )
                )
            } else {
                cameraList = cameras
                _state.postValue(
                    UiState.CameraListState(
                        loadState = LoadState.SUCCESS,
                        cameraList = List(cameraList.size) { CameraItem(cameraList[it]) }
                    )
                )
                postSingleEvent(
                    UiEvent.ShortToast(R.string.connection_error_cache_used)
                )
            }
        }
    }

    private suspend fun searchCameras(intent: UiIntent.SearchCameras) {
        if (intent.query.isBlank()) {
            _state.postValue(
                UiState.CameraListState(
                    loadState = LoadState.SUCCESS_SEARCH,
                    cameraList = List(cameraList.size) { CameraItem(cameraList[it]) }
                )
            )
        }
        _state.postValue(
            UiState.CameraListState(
                loadState = LoadState.LOADING,
            )
        )
        val query = intent.query
        val searchCameraList = cameraList.filter { it.title.contains(query, true) }
        if (query != intent.query) {
            return
        }
        _state.postValue(
            UiState.CameraListState(
                loadState = LoadState.SUCCESS_SEARCH,
                cameraList = List(searchCameraList.size) { CameraItem(searchCameraList[it]) }
            )
        )
    }

    private fun goToCamera(camera: Camera) {
        _state.postValue(
            UiState.CameraState(
                title = camera.title,
                url = VIDEO_URL_START + camera.id + VIDEO_URL_END
            )
        )
    }

    // post single event to activity
    private suspend fun postSingleEvent(event: UiEvent) {
        _singleEvent.send(event)
    }
}