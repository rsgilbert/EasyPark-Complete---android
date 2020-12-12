package com.gilboot.easypark.parklogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragParkloginBinding
import com.gilboot.easypark.util.saveUserToPrefs
import com.gilboot.easypark.util.withAuthPark
import org.jetbrains.anko.support.v4.toast


class ParkloginFragment : Fragment() {

    lateinit var binding: FragParkloginBinding

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
    withAuthPark(binding.editEmail.text.toString(), binding.editPassword.text.toString()) {
        when (it) {
            null -> toast("Failed to login")
            else -> {
                toast(it.toString())
                requireContext().saveUserToPrefs(it.user)
                navigateToDashboard()
            }
        }
    }
}

fun ParkloginFragment.navigateToDashboard() {
    val action = ParkloginFragmentDirections.actionParkloginFragmentToDashboardFragment()
    findNavController().navigate(action)
}