package com.rankor.cameraapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.rankor.cameraapplication.ui.events.UiState
import com.rankor.cameraapplication.ui.views.MainViewModel

abstract class BaseFragment(@LayoutRes val contentLayoutId: Int) : Fragment() {

    protected lateinit var viewModel: MainViewModel
    protected var _binding: ViewBinding? = null
    protected lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(contentLayoutId, container, false)
        _binding = bindView(view)
        return _binding!!.root
    }

    // to simplify binding process inside inheritor fragment class
    abstract fun bindView(view: View): ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = findNavController()
        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        if (checkFullScreen()) hideSystemUI() else showSystemUI()

    }

    // to render state inside inheritor fragment class
    abstract fun render(state: UiState)

    open fun checkFullScreen(): Boolean = false

    protected fun hideSystemUI() {
        Log.e("TAG", "hide")
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, _binding!!.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        Log.e("TAG", "show")
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        WindowInsetsControllerCompat(requireActivity().window, _binding!!.root).show(
            WindowInsetsCompat.Type.systemBars()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}