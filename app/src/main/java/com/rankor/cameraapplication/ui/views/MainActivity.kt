package com.rankor.cameraapplication.ui.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rankor.cameraapplication.databinding.ActivityMainBinding
import com.rankor.cameraapplication.ui.events.UiEvent
import com.rankor.cameraapplication.ui.events.UiIntent
import com.rankor.cameraapplication.ui.events.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycleScope.launch {
            viewModel.singleEvent.consumeAsFlow().collect { event ->
                when (event) {
                    is UiEvent.ShortToast -> {
                        Toast.makeText(this@MainActivity, event.messageId, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (viewModel.state.value is UiState.CameraState) {
            viewModel.dispatch(UiIntent.GetCameras)
            return
        }
        super.onBackPressed()
    }
}