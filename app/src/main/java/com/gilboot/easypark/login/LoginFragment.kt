package com.gilboot.easypark.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.data.User
import com.gilboot.easypark.data.UserType
import com.gilboot.easypark.databinding.FragLoginBinding
import com.gilboot.easypark.util.getUserFromPrefs


// starting fragment
// this is where a user logs in from
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragLoginBinding>(
                inflater,
                R.layout.frag_login,
                container,
                false
            )

        binding.btnSignup.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToInfoFragment2()
            findNavController().navigate(action)
        }

        navigateToCorrectFragment()





        return binding.root
    }
}

// check if there is user saved in shared preferences
// use that user's type field to go to correct fragment
fun LoginFragment.navigateToCorrectFragment() {
    val user: User? = context?.getUserFromPrefs()
    user?.let {
        when (it.type) {
            UserType.Driver -> navigateToParks()
            UserType.Park -> navigateToDashboard()
        }
    }
}

fun LoginFragment.navigateToParks() {
    val action = LoginFragmentDirections.actionLoginFragmentToParksFragment()
    findNavController().navigate(action)
}

fun LoginFragment.navigateToDashboard() {
    val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
    findNavController().navigate(action)
}

fun LoginFragment.navigateToSignup() {
    val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
    findNavController().navigate(action)
}