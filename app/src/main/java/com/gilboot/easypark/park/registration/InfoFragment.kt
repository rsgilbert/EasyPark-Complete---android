package com.gilboot.easypark.park.registration


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.data.Park
import com.gilboot.easypark.databinding.FragInfoRegBinding
import org.jetbrains.anko.support.v4.longToast
import timber.log.Timber


// Fragment for entering the name of the park and telephone of
// operator at that park
class InfoFragment : Fragment() {
//    private val parkViewModel: ParkViewModel by activityViewModels()
//    lateinit var binding: FragInfoRegBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        setHasOptionsMenu(true)
//        binding =
//            DataBindingUtil.inflate<FragInfoRegBinding>(
//                inflater,
//                R.layout.frag_info_reg,
//                container,
//                false
//            )
//
//        Timber.i("Data for park info is ${parkViewModel.parkLiveData.value}")
//
//        // set park to that which was passed in from sign up
//        parkViewModel.parkLiveData.value = parkInArgs
//
//        binding.parkViewModel = parkViewModel
//        binding.apply {
//            lifecycleOwner = viewLifecycleOwner
//
//        }
////
//
//
//
//        return binding.root
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.park_reg_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.next -> {
//                binding.apply {
//                    when {
//                        editName.text.isBlank() ->
//                            longToast("Name cannot be blank")
//
//                        editTel.text.isBlank() ->
//                            longToast("Telephone number cannot be blank")
//
//                        else -> {
//                            parkViewModel!!.setParkInfo(
//                                editName.text.toString(),
//                                editTel.text.toString()
//                            )
//                            navigateToPictureFragment()
//                        }
//                    }
//                }
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//
//        return true
//    }
}

//val InfoFragment.parkInArgs: Park
//    get() = InfoFragmentArgs.fromBundle(arguments!!).park
//
//
//fun InfoFragment.navigateToPictureFragment() {
//    val action = InfoFragmentDirections.actionInfoFragmentToPictureFragment()
//    findNavController().navigate(action)
//}