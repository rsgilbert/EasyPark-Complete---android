package com.gilboot.easypark.reservelist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gilboot.easypark.databinding.ReserveListItemBinding
import com.gilboot.easypark.model.Visit


// Adapter for reserve list
class ReserveListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Visit, ReserveListAdapter.ViewHolder>(
        ReserveDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reserve: Visit = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(reserve)
        }
        holder.bind(reserve)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(private val binding: ReserveListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reserve: Visit) {
            binding.reserve = reserve
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ReserveListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (reserve: Visit) -> Unit) {
        fun onClick(reserve: Visit) = clickListener(reserve)
    }
}

class ReserveDiffCallback : DiffUtil.ItemCallback<Visit>() {
    override fun areItemsTheSame(oldReserve: Visit, newReserve: Visit) =
        oldReserve == newReserve

    override fun areContentsTheSame(oldReserve: Visit, newReserve: Visit) =
        oldReserve == newReserve
}
