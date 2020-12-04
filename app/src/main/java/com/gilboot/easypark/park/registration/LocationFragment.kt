package com.gilboot.easypark.park.registration


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragLocationRegBinding


// Set the location of the Park
class LocationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragLocationRegBinding>(
                inflater,
                R.layout.frag_location_reg,
                container,
                false
            )


        return binding.root
    }
}