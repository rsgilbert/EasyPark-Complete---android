package com.gilboot.easypark.park

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragParkBinding
import com.gilboot.easypark.setCorrectDrawerMenu
import com.gilboot.easypark.util.repository


class ParkFragment : Fragment() {
    private val parkViewModel: ParkViewModel by viewModels {
        ParkViewModelFactory(
            parkIdFromArgs,
            repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragParkBinding>(
                inflater,
                R.layout.frag_park,
                container,
                false
            )
        setCorrectDrawerMenu()

        binding.parkViewModel = parkViewModel

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}


val ParkFragment.parkIdFromArgs
    get() = ParkFragmentArgs.fromBundle(arguments!!).parkId


// finished park of christ
