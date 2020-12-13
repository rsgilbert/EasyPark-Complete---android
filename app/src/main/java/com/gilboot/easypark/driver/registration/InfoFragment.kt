package com.gilboot.easypark.driver.registration


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragInfoDriverRegBinding


// Fragment for entering the name of the park and telephone of
// operator at that park
class InfoFragment : Fragment() {
//    private val driverViewModel: DriverViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        setHasOptionsMenu(true)
//        val binding =
//            DataBindingUtil.inflate<FragInfoDriverRegBinding>(
//                inflater,
//                R.layout.frag_info_driver_reg,
//                container,
//                false
//            )
//
//
//        driverViewModel.driverLiveData.value = driverFromArgs
//        binding.driverViewModel = driverViewModel
//        binding.apply {
//            lifecycleOwner = viewLifecycleOwner
//
//        }
//
//
//
//        return binding.root
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.driver_info_reg_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.next -> {
//                navigateToVehicleFragment()
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//
//        return true
//    }
}

//fun InfoFragment.navigateToVehicleFragment() {
//    val action = InfoFragmentDirections.actionDriverInfoFragmentToVehicleFragment()
//    findNavController().navigate(action)
//}
//
//val InfoFragment.driverFromArgs
//    get() = InfoFragmentArgs.fromBundle(arguments!!).driver