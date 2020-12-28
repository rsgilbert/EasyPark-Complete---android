package com.gilboot.easypark.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.model.UserType
import com.gilboot.easypark.databinding.FragChooseBinding
import com.gilboot.easypark.util.emptyDatabase
import com.gilboot.easypark.util.getUserFromPrefs
import com.gilboot.easypark.util.repository
import kotlinx.coroutines.delay
import timber.log.Timber


/**
 * We choose which fragment to go to, driver or park
 */

class ChooseFragment : Fragment() {

    private val chooseViewModel: ChooseViewModel by viewModels { ChooseViewModelFactory(repository) }
    lateinit var binding: FragChooseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        chooseViewModel.userTypeLiveData.observe(viewLifecycleOwner, Observer {
            Timber.i("emitted $it")
            it?.let { userType ->
                when (userType) {
                    UserType.Driver -> navigateToParks()
                    UserType.Park -> navigateToDashboard()
                    else -> {
                        Timber.i("User type is $userType")
                        binding.linearLayout.visibility = View.VISIBLE
                        binding.splashScreen.visibility = View.GONE
                        startFromAfresh()
                    }
                }
            }
        })


        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.frag_choose,
                container,
                false
            )

        binding.buttonDriver.setOnClickListener { navigateToDriverlogin() }
        binding.buttonPark.setOnClickListener { navigateToParklogin() }


        return binding.root
    }
}

fun ChooseFragment.navigateToDriverlogin() {
    findNavController().navigate(ChooseFragmentDirections.actionChooseFragmentToDriverloginFragment())
}

fun ChooseFragment.navigateToParklogin() {
    findNavController().navigate(ChooseFragmentDirections.actionChooseFragmentToParkloginFragment())
}

fun ChooseFragment.navigateToParks() {
    findNavController().navigate(ChooseFragmentDirections.actionChooseFragmentToParksFragment())
}

fun ChooseFragment.navigateToDashboard() {
    findNavController().navigate(ChooseFragmentDirections.actionChooseFragmentToDashboardFragment())
}

fun ChooseFragment.startFromAfresh() {
    emptyDatabase()
}