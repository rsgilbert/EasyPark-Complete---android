package com.gilboot.easypark.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragChooseBinding


// This is where we choose which fragment to go to, driver or park
class ChooseFragment : Fragment() {

    lateinit var binding: FragChooseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate<FragChooseBinding>(
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