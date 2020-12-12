package com.gilboot.easypark.driverlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragDriverloginBinding
import com.gilboot.easypark.util.saveUserToPrefs
import com.gilboot.easypark.util.withAuthDriver
import org.jetbrains.anko.support.v4.toast


// This is where we choose which fragment to go to, driver or park
class DriverloginFragment : Fragment() {

    lateinit var binding: FragDriverloginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.frag_driverlogin,
                container,
                false
            )


        binding.textCreateAccount.setOnClickListener {
            navigateToDriversignup()
        }

        binding.buttonLogin.setOnClickListener {
            attemptLogin()
        }

        return binding.root
    }

}

fun DriverloginFragment.attemptLogin() {
    withAuthDriver(binding.editEmail.text.toString(), binding.editPassword.text.toString()) {
        when (it) {
            null -> toast("Failed to login")
            else -> {
                toast(it.toString())
                requireContext().saveUserToPrefs(it.user)
                navigateToParksFragment()
            }
        }
    }
}

fun DriverloginFragment.navigateToDriversignup() {
    findNavController().navigate(DriverloginFragmentDirections.actionDriverloginFragmentToDriversignupFragment())
}

fun DriverloginFragment.navigateToParksFragment() {
    val action = DriverloginFragmentDirections.actionDriverloginFragmentToParksFragment()
    findNavController().navigate(action)
}