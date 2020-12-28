package com.gilboot.easypark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.databinding.FragParksBinding
import com.gilboot.easypark.util.emptyDatabase
import com.gilboot.easypark.util.removeUserFromPrefs
import com.gilboot.easypark.util.repository

class SignoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // empty room database
        emptyDatabase()

        // navigate to chooseFragment
        requireContext().removeUserFromPrefs()
        setCorrectDrawerMenu()
        navigateToChooseFragment()


        val binding =
            DataBindingUtil.inflate<FragParksBinding>(
                inflater,
                R.layout.frag_parks,
                container,
                false
            )

        return binding.root
    }
}

fun SignoutFragment.navigateToChooseFragment() {
    findNavController().navigate(SignoutFragmentDirections.actionSignoutFragmentToChooseFragment())
}