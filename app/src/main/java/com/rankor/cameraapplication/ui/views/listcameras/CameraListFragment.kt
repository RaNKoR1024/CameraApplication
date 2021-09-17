package com.rankor.cameraapplication.ui.views.listcameras

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.rankor.cameraapplication.data.LoadState
import com.rankor.cameraapplication.databinding.FragmentCameraListBinding
import com.rankor.cameraapplication.ui.BaseFragment
import com.rankor.cameraapplication.ui.events.UiIntent
import com.rankor.cameraapplication.ui.events.UiState
import com.rankor.cameraapplication.ui.views.setLoadingState


class CameraListFragment :
    BaseFragment(com.rankor.cameraapplication.R.layout.fragment_camera_list) {

    private lateinit var adapter: CameraListAdapter
    private val binding
        get() = _binding!! as FragmentCameraListBinding

    override fun bindView(view: View): ViewBinding = FragmentCameraListBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CameraListAdapter(requireContext()) {
            viewModel.dispatch(UiIntent.GoToCamera.apply {
                camera = it.camera
            })
        }
        with(binding) {
            val density = resources.displayMetrics.density
            val columns = (resources.displayMetrics.widthPixels / (300 * density)).toInt()
            val gridColumns = if (columns == 0) 1 else columns
            rvCameras.adapter = adapter
            rvCameras.layoutManager = GridLayoutManager(requireContext(), gridColumns)
            loadScreen.btnRetry.setOnClickListener {
                viewModel.dispatch(UiIntent.GetCameras)
            }
            viewModel.dispatch(UiIntent.GetCameras)
            val searchView = tbTitle.menu.findItem(
                com.rankor.cameraapplication.R.id.action_search
            ).actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        viewModel.dispatch(UiIntent.SearchCameras.apply {
                            query = newText.lowercase()
                        })
                    }
                    return true
                }
            })
            val searchIcon: ImageView =
                searchView.findViewById(androidx.appcompat.R.id.search_button)
            searchIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.rankor.cameraapplication.R.drawable.ic_baseline_search
                )
            )
        }
    }

    override fun render(state: UiState) {
        when (state) {
            is UiState.CameraListState -> renderThisState(state)
            is UiState.CameraState -> {
                navController.navigate(
                    com.rankor.cameraapplication.R.id.action_listCamerasFragment_to_cameraFragment
                )
            }
        }
    }

    private fun renderThisState(state: UiState.CameraListState) {
        with(state) {
            binding.loadScreen.setLoadingState(loadState)
            adapter.isClickAllowed =
                loadState == LoadState.SUCCESS || loadState == LoadState.SUCCESS_SEARCH
            if (state.cameraList.isNotEmpty() || state.loadState == LoadState.SUCCESS_SEARCH) {
                adapter.setData(cameraList)
            }
        }
    }
}