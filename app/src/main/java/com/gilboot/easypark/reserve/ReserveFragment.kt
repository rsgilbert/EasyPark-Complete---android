package com.gilboot.easypark.reserve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragParkBinding
import com.gilboot.easypark.databinding.FragReserveBinding
import com.gilboot.easypark.park.*
import com.gilboot.easypark.setCorrectDrawerMenu
import com.gilboot.easypark.util.repository

class ReserveFragment : Fragment() {
    private val reserveViewModel: ReserveViewModel by viewModels {
        ReserveViewModelFactory(
            reserveFromArgs
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragReserveBinding>(
                inflater,
                R.layout.frag_reserve,
                container,
                false
            )

        binding.reserveViewModel = reserveViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}


val ReserveFragment.reserveFromArgs
    get() = ReserveFragmentArgs.fromBundle(arguments!!).reserve