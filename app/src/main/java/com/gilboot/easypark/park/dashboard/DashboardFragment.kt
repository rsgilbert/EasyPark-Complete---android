package com.gilboot.easypark.park.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gilboot.easypark.MainActivity
import com.gilboot.easypark.R
import com.gilboot.easypark.checkLogin
import com.gilboot.easypark.databinding.FragDashboardParkBinding
import org.jetbrains.anko.support.v4.toast


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

        recheckLogin()
        binding.dashboardViewModel = dashboardViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            visitList.adapter = DashboardAdapter(dashboardOnClickListener)
        }


        return binding.root


    }


}

fun Fragment.recheckLogin() {
    (activity as MainActivity).checkLogin()
}


val DashboardFragment.dashboardOnClickListener: DashboardAdapter.OnClickListener
    get() = DashboardAdapter.OnClickListener {
        toast("visit is $it")
    }
