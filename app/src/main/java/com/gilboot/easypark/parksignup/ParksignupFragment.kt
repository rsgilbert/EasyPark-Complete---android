package com.gilboot.easypark.parksignup

//import com.gilboot.easypark.parksignup.ParksignupFragment
//import com.gilboot.easypark.parksignup.ParksignupFragmentDirections.actionParksignupFragmentToDashboardFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.databinding.FragParksignupBinding
import com.gilboot.easypark.util.isValidInput
import com.gilboot.easypark.util.repository
import com.gilboot.easypark.util.textValue
import org.jetbrains.anko.support.v4.toast


// This is where we choose which fragment to go to, driver or park
class ParksignupFragment : Fragment() {

    lateinit var binding: FragParksignupBinding
    private val parkinfoViewModel: ParkinfoViewModel by activityViewModels() {
        ParkinfoViewModelFactory(repository)
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
                R.layout.frag_parksignup,
                container,
                false
            )

        binding.parkinfoViewModel = parkinfoViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        binding.textLogin.setOnClickListener {
            navigateToParklogin()
        }

        binding.buttonSignup.setOnClickListener {
            saveInfoAndNavigate()

        }

        return binding.root
    }

}

fun ParksignupFragment.saveInfoAndNavigate() {
    binding.apply {
        when {
            !editEmail.isValidInput() -> toast("Invalid email")
            !editPassword.isValidInput() -> toast("Invalid password")
            !editTel.isValidInput() -> toast("Invalid telephone number")
            else -> {
                parkinfoViewModel!!.apply {
                    setEmail(editEmail.textValue())
                    setPassword(editPassword.textValue())
                    setTelephone(editTel.textValue())

                    navigateToParkinfo()
                }
            }
        }
    }
}

fun ParksignupFragment.navigateToParklogin() {
    findNavController().popBackStack()
}


fun ParksignupFragment.navigateToParkinfo() {
    findNavController().navigate(
        ParksignupFragmentDirections.actionParksignupFragmentToParkinfoFragment()
    )
}
