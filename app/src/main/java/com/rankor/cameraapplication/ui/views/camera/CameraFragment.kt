package com.rankor.cameraapplication.ui.views.camera

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.rankor.cameraapplication.R
import com.rankor.cameraapplication.databinding.FragmentCameraBinding
import com.rankor.cameraapplication.ui.BaseFragment
import com.rankor.cameraapplication.ui.events.UiIntent
import com.rankor.cameraapplication.ui.events.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraFragment : BaseFragment(R.layout.fragment_camera) {

    private val binding
        get() = _binding!! as FragmentCameraBinding

    override fun bindView(view: View): ViewBinding = FragmentCameraBinding.bind(view)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tbTitle.setNavigationOnClickListener {
            viewModel.dispatch(UiIntent.GetCameras)
        }
        with (binding) {
            wvCamera.webChromeClient = WebChromeClient()
            wvCamera.webViewClient = WebViewClient()
            wvCamera.settings.javaScriptEnabled = true
        }
    }

    override fun render(state: UiState) {
        when(state) {
            is UiState.CameraState -> renderThisState(state)
            is UiState.CameraListState -> navController.popBackStack()
        }
    }

    private fun renderThisState(state: UiState.CameraState) {
        with(binding) {
            lifecycleScope.launch(Dispatchers.Main) {
                wvCamera.loadUrl(state.url)
                // strange bug with status bar
                delay(500L)
                hideSystemUI()
            }
            tbTitle.title = state.title
        }
    }

    override fun checkFullScreen(): Boolean = true
}