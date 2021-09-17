package com.rankor.cameraapplication.ui.views

import android.view.View
import com.rankor.cameraapplication.data.LoadState
import com.rankor.cameraapplication.databinding.ViewLoadBinding

fun ViewLoadBinding.setLoadingState(loadingState: LoadState) {
    with(this) {
        when (loadingState) {
            LoadState.LOADING -> {
                pbPreloader.visibility = View.VISIBLE
                btnRetry.visibility = View.GONE
                tvErrorName.visibility = View.GONE
                root.visibility = View.VISIBLE
            }
            LoadState.ERROR -> {
                pbPreloader.visibility = View.GONE
                btnRetry.visibility = View.VISIBLE
                tvErrorName.visibility = View.VISIBLE
                root.visibility = View.VISIBLE
            }
            LoadState.SUCCESS, LoadState.SUCCESS_SEARCH -> {
                root.visibility = View.GONE
                pbPreloader.visibility = View.GONE
                btnRetry.visibility = View.GONE
                tvErrorName.visibility = View.GONE
            }
        }
    }
}