package com.gilboot.easypark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gilboot.easypark.databinding.PictureItemBinding


// Adapter for picture list
class PictureAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<String, PictureAdapter.ViewHolder>(
        ItemDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture: String = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(picture)
        }
        holder.bind(picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(private val binding: PictureItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(picture: String) {
            binding.picture = picture
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PictureItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (picture: String) -> Unit) {
        fun onClick(picture: String) = clickListener(picture)
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldPicture: String, newPicture: String) =
        oldPicture == newPicture

    override fun areContentsTheSame(oldPicture: String, newPicture: String) =
        oldPicture == newPicture
}
