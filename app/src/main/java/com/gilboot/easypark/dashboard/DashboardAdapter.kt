package com.gilboot.easypark.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.databinding.DashboardItemBinding


// Adapter for dashboard with visits list
class DashboardAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Visit, DashboardAdapter.ViewHolder>(
        ItemDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val visit: Visit = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(visit)
        }
        holder.bind(visit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(private val binding: DashboardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(visit: Visit) {
            binding.visit = visit
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DashboardItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (visit: Visit) -> Unit) {
        fun onClick(visit: Visit) = clickListener(visit)
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<Visit>() {
    override fun areItemsTheSame(oldVisit: Visit, newVisit: Visit) =
        oldVisit._id == newVisit._id

    override fun areContentsTheSame(oldVisit: Visit, newVisit: Visit) =
        oldVisit == newVisit

}
