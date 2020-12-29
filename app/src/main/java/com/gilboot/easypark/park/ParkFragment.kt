package com.gilboot.easypark.park

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragParkBinding
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.setCorrectDrawerMenu
import com.gilboot.easypark.util.longSnackbar
import com.gilboot.easypark.util.repository
import com.gilboot.easypark.util.dialPhoneNumber

/**
 * Park Fragment
 * Shows information about a single park
 * Allows making slot reservations
 */
class ParkFragment : Fragment() {
    val parkViewModel: ParkViewModel by viewModels {
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
            reserve.setOnClickListener { reserveSlot() }
        }

        observeSnackMessage()
        observeNavigateToReserve()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.park_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.map -> navigateToMap(parkViewModel.parkLiveData.value!!)
            R.id.call -> requireContext().dialPhoneNumber(parkViewModel.parkLiveData.value!!.tel)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}


val ParkFragment.parkIdFromArgs
    get() = ParkFragmentArgs.fromBundle(arguments!!).parkId

fun ParkFragment.navigateToMap(park: Park) {
    findNavController().navigate(
        ParkFragmentDirections.actionParkFragmentToParklocationFragment(
            park
        )
    )
}

fun ParkFragment.reserveSlot() {
    parkViewModel.reserveSlot()
}


fun ParkFragment.observeSnackMessage() {
    parkViewModel.snackMessageLiveData.observe(viewLifecycleOwner, Observer {
        it?.let { event ->
            event.getContentIfNotHandledOrReturnNull()?.let { msg ->
                longSnackbar(msg)
            }
        }
    })
}

fun ParkFragment.observeNavigateToReserve() {
    parkViewModel.navigateToReserveLiveData.observe(viewLifecycleOwner, Observer {
        it?.let { reserve ->
            findNavController().navigate(
                ParkFragmentDirections.actionParkFragmentToReserveFragment(
                    reserve
                )
            )
            parkViewModel.navigateToReserveComplete()
        }
    })
}

// 28/12/2020, lunch time call
// Milcah's church
// finished park of christ
