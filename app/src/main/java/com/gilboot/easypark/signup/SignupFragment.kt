package com.gilboot.easypark.signup

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragSignupBinding
import org.jetbrains.anko.support.v4.toast


class SignupFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<FragSignupBinding>(inflater, R.layout.frag_signup, container,
                false)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sign_up, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signup -> {
                toast("Sign up")
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}