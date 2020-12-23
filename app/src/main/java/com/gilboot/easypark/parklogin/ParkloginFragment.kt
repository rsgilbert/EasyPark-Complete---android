package com.gilboot.easypark.parklogin

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
import com.gilboot.easypark.databinding.FragParkloginBinding
import com.gilboot.easypark.util.isValidInput
import com.gilboot.easypark.util.repository
import com.gilboot.easypark.util.textValue
import org.jetbrains.anko.support.v4.toast


class ParkloginFragment : Fragment() {

    lateinit var binding: FragParkloginBinding
    val parkloginViewModel: ParkloginViewModel by viewModels() {
        ParkloginViewModelFactory(repository = repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.frag_parklogin,
                container,
                false
            )

        parkloginViewModel.navigateToDashboardLiveData.observe(
            viewLifecycleOwner,
            Observer { navigate ->
                navigate?.let {
                    navigateToDashboard()
                    parkloginViewModel.navigateToDashboardComplete()
                }
            })


        binding.textCreateAccount.setOnClickListener {
            navigateToSignup()
        }

        binding.buttonLogin.setOnClickListener {
            attemptLogin()
        }
        return binding.root
    }


}

fun ParkloginFragment.navigateToSignup() {
    findNavController().navigate(ParkloginFragmentDirections.actionParkloginFragmentToParksignupFragment())
}

fun ParkloginFragment.attemptLogin() {
    when {
        !binding.editEmail.isValidInput() -> toast("Invalid email")
        !binding.editPassword.isValidInput() -> toast("invalid Password")
        else -> {
            parkloginViewModel.loginPark(
                binding.editEmail.textValue(),
                binding.editPassword.textValue()
            )
        }
    }

}

fun ParkloginFragment.navigateToDashboard() {
    val action = ParkloginFragmentDirections.actionParkloginFragmentToDashboardFragment()
    findNavController().navigate(action)
}