package com.gilboot.easypark.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.MainActivity
import com.gilboot.easypark.R
import com.gilboot.easypark.setCorrectDrawerMenu
import com.gilboot.easypark.databinding.FragDashboardParkBinding
import com.gilboot.easypark.dialogs.NumberplateDialog
import com.gilboot.easypark.util.getUserFromPrefs
import com.gilboot.easypark.util.repository


// Dashboard where the park records new vehicles
// that have visited the park
// The park also removes vehicles that have left
class DashboardFragment : Fragment() {
    private val dashboardViewModel: DashboardViewModel by activityViewModels {
        DashboardViewModelFactory(repository)
    }

    val dashboardAdapter = DashboardAdapter(dashboardOnClickListener)


    lateinit var binding: FragDashboardParkBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        setCorrectDrawerMenu()
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.frag_dashboard_park,
                container,
                false
            )


//        recheckLogin()
        binding.dashboardViewModel = dashboardViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            visitList.adapter = dashboardAdapter
            fabAdd.setOnClickListener {
                showNumberplateDialog()
                dashboardViewModel?.getVisits()
            }
        }


        return binding.root


    }


}

fun Fragment.recheckLogin() {
    (activity as MainActivity).setCorrectDrawerMenu()
}


val DashboardFragment.dashboardOnClickListener: DashboardAdapter.OnClickListener
    get() = DashboardAdapter.OnClickListener {
        navigateToVisit(it._id)
    }

fun DashboardFragment.navigateToVisit(visitId: String) {
    findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToVisitFragment(visitId))
}

fun Fragment.showNumberplateDialog() {
    NumberplateDialog().show(childFragmentManager, "numberplate dialog")
}

