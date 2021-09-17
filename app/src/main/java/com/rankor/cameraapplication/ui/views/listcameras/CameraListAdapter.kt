package com.rankor.cameraapplication.ui.views.listcameras

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rankor.cameraapplication.R
import com.rankor.cameraapplication.databinding.ItemCameraBinding

class CameraListAdapter(
    private val context: Context,
    private val onClick: (CameraItem) -> Unit
) : RecyclerView.Adapter<CameraListAdapter.ViewHolder>() {

    private var dataSet = emptyList<CameraItem>()
    var isClickAllowed = true

    class ViewHolder(val binding: ItemCameraBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCameraBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        with(viewHolder.binding) {
            tvCameraName.text = item.camera.title
            Glide.with(context)
                .load(item.url)
                .override(200, 100)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_camera_indoor)
                .error(R.drawable.ic_baseline_camera_indoor)
                .into(ivCamera)

            mainContainer.setOnClickListener {
                if (isClickAllowed) {
                    isClickAllowed = false
                    onClick.invoke(item)
                }
            }
        }
    }

    override fun getItemCount() = dataSet.size

    fun setData(newDataSet: List<CameraItem>) {
        if (dataSet == newDataSet) {
            return
        }
        if (dataSet.isNotEmpty()) {
            notifyItemRangeRemoved(0, dataSet.size)
        }
        dataSet = newDataSet
        notifyItemRangeInserted(0, dataSet.size)
    }
}