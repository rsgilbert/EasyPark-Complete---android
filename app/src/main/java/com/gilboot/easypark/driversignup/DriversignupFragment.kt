package com.gilboot.easypark.driversignup

//import com.gilboot.easypark.parksignup.DriversignupFragment
//import com.gilboot.easypark.parksignup.DriversignupFragmentDirections.actionDriversignupFragmentToDashboardFragment
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
import com.gilboot.easypark.databinding.FragDriversignupBinding
import com.gilboot.easypark.driversignup.*
import com.gilboot.easypark.util.*
import org.jetbrains.anko.support.v4.toast


// This is where we choose which fragment to go to, driver or park
class DriversignupFragment : Fragment() {

    lateinit var binding: FragDriversignupBinding
    val driversignupViewModel: DriversignupViewModel by viewModels() {
        DriversignupViewModelFactory(repository)
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
                R.layout.frag_driversignup,
                container,
                false
            )

        driversignupViewModel.navigateToParksLiveData.observe(
            viewLifecycleOwner,
            Observer { navigate ->
                navigate?.let {
                    navigateToParksStart()
                    driversignupViewModel.navigateToParksComplete()
                }
            })


        binding.buttonSignup.setOnClickListener {
            attemptSignup()
        }

        binding.textLogin.setOnClickListener {
            navigateToDriverlogin()
        }
        return binding.root
    }


}

fun DriversignupFragment.navigateToParksStart() {
    findNavController().navigate(DriversignupFragmentDirections.actionDriversignupFragmentToParksFragment())
}


fun DriversignupFragment.attemptSignup() {
    when {
        !binding.editEmail.isValidInput() -> toast("Invalid email")
        !binding.editPassword.isValidInput() -> toast("invalid Password")
        else -> {
            driversignupViewModel.signupDriver(
                binding.editEmail.textValue(),
                binding.editPassword.textValue()
            )
        }
    }
}

fun DriversignupFragment.navigateToDriverlogin() {
    findNavController().popBackStack()
}

fun DriversignupFragment.navigateToParksFragment() {
    val action = DriversignupFragmentDirections.actionDriversignupFragmentToParksFragment()
    findNavController().navigate(action)
}