package com.microsoft.device.display.samples.listdetail.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.samples.listdetail.R
import kotlinx.android.synthetic.main.layout_image_item.view.*

class ImageAdapter(
    context: Context,
    private val images: List<Int>,
    private val onClick: (item: Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            layoutInflater.inflate(R.layout.layout_image_item, parent, false)
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position], onClick)
    }

    override fun getItemCount() = images.size

    fun getItem(pos: Int) = images.takeIf { images.size > pos && pos >= 0 }?.get(pos)

    fun getItemPosition(item: Int) = images.indexOf(item)

//    fun selectItem(pos: Int) {
//        val oldPosition = selectedPosition
//        selectedPosition = pos
//        notifyItemChanged(selectedPosition)
//        notifyItemChanged(oldPosition)
//    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.image_item as ImageView

        fun bind(image: Int, onClick: (image: Int) -> Unit) {
            imageView?.setImageResource(image)
            itemView.setOnClickListener { onClick(image) }
        }
    }

}