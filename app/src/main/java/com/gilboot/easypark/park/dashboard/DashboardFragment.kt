package com.gilboot.easypark.park.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragDashboardParkBinding


// Dashboard where the park records new vehicles
// that have visited the park
// The park also removes vehicles that have left
class DashboardFragment : Fragment() {
    val dashboardViewModel: DashboardViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragDashboardParkBinding>(
                inflater,
                R.layout.frag_dashboard_park,
                container,
                false
            )

        binding.dashboardViewModel = dashboardViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }


        return binding.root


    }


}