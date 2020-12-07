package com.gilboot.easypark.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragLoginBinding


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




        return binding.root
    }
}