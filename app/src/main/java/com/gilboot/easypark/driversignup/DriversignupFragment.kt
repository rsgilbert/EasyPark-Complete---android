package com.gilboot.easypark.driversignup

//import com.gilboot.easypark.parksignup.DriversignupFragment
//import com.gilboot.easypark.parksignup.DriversignupFragmentDirections.actionDriversignupFragmentToDashboardFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragDriversignupBinding
import com.gilboot.easypark.util.saveUserToPrefs
import com.gilboot.easypark.util.withAuthDriverSignup
import org.jetbrains.anko.support.v4.toast


// This is where we choose which fragment to go to, driver or park
class DriversignupFragment : Fragment() {

    lateinit var binding: FragDriversignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.frag_driversignup,
                container,
                false
            )


        binding.textLogin.setOnClickListener {
            navigateToDriverlogin()
        }

        binding.buttonSignup.setOnClickListener {
            attemptsignup()
        }

        return binding.root
    }

}

fun DriversignupFragment.attemptsignup() {
    withAuthDriverSignup(binding.editEmail.text.toString(), binding.editPassword.text.toString()) {
        when (it) {
            null -> toast("Failed to signup")
            else -> {
                toast(it.toString())
                requireContext().saveUserToPrefs(it.user)
                navigateToParksFragment()
            }
        }
    }
}

fun DriversignupFragment.navigateToDriverlogin() {
    findNavController().navigate(DriversignupFragmentDirections.actionDriversignupFragmentToDriverloginFragment())
}

fun DriversignupFragment.navigateToParksFragment() {
    val action = DriversignupFragmentDirections.actionDriversignupFragmentToParksFragment()
    findNavController().navigate(action)
}