package com.gilboot.easypark.driverlogin

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
import com.gilboot.easypark.databinding.FragDriverloginBinding
import com.gilboot.easypark.driversignup.DriversignupFragment
import com.gilboot.easypark.util.isValidInput
import com.gilboot.easypark.util.longSnackbar
import com.gilboot.easypark.util.repository
import com.gilboot.easypark.util.textValue
import org.jetbrains.anko.support.v4.toast


class DriverloginFragment : Fragment() {

    lateinit var binding: FragDriverloginBinding
    val driverloginViewModel: DriverloginViewModel by viewModels() {
        DriverloginViewModelFactory(repository)
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
                R.layout.frag_driverlogin,
                container,
                false
            )

        driverloginViewModel.navigateToParksLiveData.observe(
            viewLifecycleOwner,
            Observer { navigate ->
                navigate?.let {
                    navigateToParksStart()
                    driverloginViewModel.navigateToParksComplete()
                }
            })


        binding.textCreateAccount.setOnClickListener {
            navigateToSignup()
        }

        binding.buttonLogin.setOnClickListener {
            attemptLogin()
        }

        observeSnackMessage()

        return binding.root
    }


}

fun DriverloginFragment.navigateToParksStart() {
    findNavController().navigate(DriverloginFragmentDirections.actionDriverloginFragmentToParksFragment())
}


fun DriverloginFragment.navigateToSignup() {
    findNavController().navigate(DriverloginFragmentDirections.actionDriverloginFragmentToDriversignupFragment())
}

fun DriverloginFragment.attemptLogin() {
    when {
        !binding.editEmail.isValidInput() -> toast("Invalid email")
        !binding.editPassword.isValidInput() -> toast("invalid Password")
        else -> {
            driverloginViewModel.loginDriver(
                binding.editEmail.textValue(),
                binding.editPassword.textValue()
            )
        }
    }
}

fun DriverloginFragment.observeSnackMessage() {
    driverloginViewModel.snackMessageLiveData.observe(viewLifecycleOwner, Observer {
        it?.let { event ->
            event.getContentIfNotHandledOrReturnNull()?.let { msg ->
                longSnackbar(msg)
            }
        }
    })
}