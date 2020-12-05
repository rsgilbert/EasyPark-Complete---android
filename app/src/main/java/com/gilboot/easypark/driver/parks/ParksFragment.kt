package com.gilboot.easypark.driver.parks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gilboot.easypark.ParkAdapter
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragParksBinding
import org.jetbrains.anko.support.v4.toast


// Fragment to show nearby parks
// operator at that park
class ParksFragment : Fragment() {
    private val parksViewModel: ParksViewModel by viewModels()

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
        toast("Link is $it")
    }
