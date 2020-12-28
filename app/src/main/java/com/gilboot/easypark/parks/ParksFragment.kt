package com.gilboot.easypark.parks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.ParkAdapter
import com.gilboot.easypark.R
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.databinding.FragParksBinding
import com.gilboot.easypark.parklogin.ParkloginViewModelFactory
import com.gilboot.easypark.setCorrectDrawerMenu
import com.gilboot.easypark.util.repository


// Fragment to show nearby parks
// operator at that park
class ParksFragment : Fragment() {
    private val parksViewModel: ParksViewModel by viewModels() { ParksViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragParksBinding>(
                inflater,
                R.layout.frag_parks,
                container,
                false
            )

        setCorrectDrawerMenu()

        binding.parksViewModel = parksViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            parkList.adapter = ParkAdapter(parkOnClickListener)

        }


        return binding.root
    }
}

val ParksFragment.parkOnClickListener: ParkAdapter.OnClickListener
    get() = ParkAdapter.OnClickListener {
        navigateToPark(it._id)
    }

fun ParksFragment.navigateToPark(parkId: String) {
    findNavController().navigate(ParksFragmentDirections.actionParksFragmentToParkFragment(parkId))
}

