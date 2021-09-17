package com.rankor.cameraapplication.ui.views

import android.view.View
import com.rankor.cameraapplication.data.LoadState
import com.rankor.cameraapplication.databinding.ViewLoadBinding

fun ViewLoadBinding.setLoadingState(loadingState: LoadState) {
    with(this) {
        when (loadingState) {
            LoadState.LOADING -> {
                root.visibility = View.VISIBLE
                pbPreloader.visibility = View.VISIBLE
                btnRetry.visibility = View.GONE
                tvErrorName.visibility = View.GONE
            }
            LoadState.ERROR -> {
                root.visibility = View.VISIBLE
                pbPreloader.visibility = View.GONE
                btnRetry.visibility = View.VISIBLE
                tvErrorName.visibility = View.VISIBLE
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