package com.gilboot.easypark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.databinding.ParkItemBinding


// Adapter for park list
class ParkAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Park, ParkAdapter.ViewHolder>(
        ParkDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val park: Park = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(park)
        }
        holder.bind(park)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(private val binding: ParkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(park: Park) {
            binding.park = park
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ParkItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (park: Park) -> Unit) {
        fun onClick(park: Park) = clickListener(park)
    }
}

class ParkDiffCallback : DiffUtil.ItemCallback<Park>() {
    override fun areItemsTheSame(oldPark: Park, newPark: Park) =
        oldPark == newPark

    override fun areContentsTheSame(oldPark: Park, newPark: Park) =
        oldPark == newPark
}
