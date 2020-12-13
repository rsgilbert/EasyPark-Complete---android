package com.gilboot.easypark.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.data.User
import com.gilboot.easypark.data.UserType
import com.gilboot.easypark.databinding.FragChooseBinding
import com.gilboot.easypark.util.getUserFromPrefs
import org.jetbrains.anko.support.v4.toast


/**
 * We choose which fragment to go to, driver or park
 */

class ChooseFragment : Fragment() {

    lateinit var binding: FragChooseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getUserFromPrefs()?.let {
            when (it.type) {
                UserType.Driver -> navigateToParks()
                UserType.Park -> navigateToDashboard()
            }
        }

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