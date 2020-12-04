package com.gilboot.easypark.park.registration


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragInfoRegBinding
import org.jetbrains.anko.support.v4.longToast


// Fragment for entering the name of the park and telephone of
// operator at that park
class InfoFragment : Fragment() {
    private val parkViewModel: ParkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragInfoRegBinding>(
                inflater,
                R.layout.frag_info_reg,
                container,
                false
            )


        binding.parkViewModel = parkViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

        }
//
        binding.apply {
            btnToPic.setOnClickListener {
                when {
                    editName.text.isBlank() ->
                        longToast("Name cannot be blank")

                    editTel.text.isBlank() ->
                        longToast("${parkViewModel!!.parkLiveData!!.value}Telephone number cannot be blank")

                    else -> {
                        navigateToPictureFragment()
                    }
                }
            }
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.park_reg_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.next -> {
                navigateToPictureFragment()
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}

fun InfoFragment.navigateToPictureFragment() {
    val action = InfoFragmentDirections.actionInfoFragmentToPictureFragment()
    findNavController().navigate(action)
}