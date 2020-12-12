package com.gilboot.easypark.parksignup

//import com.gilboot.easypark.parksignup.ParksignupFragment
//import com.gilboot.easypark.parksignup.ParksignupFragmentDirections.actionParksignupFragmentToDashboardFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.data.Park
import com.gilboot.easypark.databinding.FragParksignupBinding


// This is where we choose which fragment to go to, driver or park
class ParksignupFragment : Fragment() {

    lateinit var binding: FragParksignupBinding

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


        binding.textLogin.setOnClickListener {
            navigateToParklogin()
        }

        binding.buttonSignup.setOnClickListener {
            navigateToParkinfo()
        }

        return binding.root
    }

}

fun ParksignupFragment.navigateToParklogin() {
    findNavController().popBackStack()
}


fun ParksignupFragment.navigateToParkinfo() {
    val park = Park(binding.editEmail.text.toString(), binding.editPassword.text.toString())
    findNavController().navigate(
        ParksignupFragmentDirections.actionParksignupFragmentToParkinfoFragment(
            park
        )
    )
}
