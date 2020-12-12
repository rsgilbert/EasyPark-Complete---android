package com.gilboot.easypark.login

import android.os.Bundle
import android.view.*
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

    lateinit var binding: FragLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate<FragLoginBinding>(
                inflater,
                R.layout.frag_login,
                container,
                false
            )

        navigateToCorrectFragment()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.login_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.login -> {
                attemptLogin()
                navigateToCorrectFragment()
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
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

fun LoginFragment.attemptLogin() {
    if (binding.driverCheckbox.isChecked) {
        // login driver

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