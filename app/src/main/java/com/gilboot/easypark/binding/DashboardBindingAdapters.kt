package com.gilboot.easypark.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gilboot.easypark.bindImage
import com.gilboot.easypark.data.Visit
import com.gilboot.easypark.park.dashboard.DashboardAdapter
import com.gilboot.easypark.util.withVehicle
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("dashboardList")
fun RecyclerView.bindDashboardList(visits: List<Visit>?) {
    visits?.let {
        (adapter as DashboardAdapter).submitList(it)
    }
}

@BindingAdapter("setDuration")
fun TextView.bindDuration(nullableVisit: Visit?) {
    nullableVisit?.let { visit ->
        val diff = visit.end - visit.start
        text = diff.toString()
    }
}

@BindingAdapter("setPlateNo")
fun TextView.bindPlateNo(vehicleId: String?) {
    vehicleId?.let { veh ->
        withVehicle(veh) {
            text = it.numberplate
        }
    }
}

@BindingAdapter("setVehicleDP")
fun CircleImageView.bindVehicleDP(vehicleId: String?) {
    vehicleId?.let { veh ->
        withVehicle(veh) {
            bindImage(it.displayPicture)
        }
    }
}