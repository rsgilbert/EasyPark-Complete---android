package com.gilboot.easypark.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragSignupBinding


class SignupFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragSignupBinding>(
                inflater,
                R.layout.frag_signup,
                container,
                false
            )


        return binding.root
    }
}