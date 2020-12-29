package com.gilboot.easypark.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gilboot.easypark.ParkAdapter
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.util.timeSpent
import java.util.*


// adapter for parks
@BindingAdapter("parkList")
fun RecyclerView.bindParkList(parks: List<Park>?) {
    parks?.let {
        (adapter as ParkAdapter).submitList(it)
    }
}

@BindingAdapter("setDuration")
fun TextView.bindDuration(nullableVisit: Visit?) {
    nullableVisit?.let { visit ->
        text = timeSpent(visit.start, Date().time)
    }
}

