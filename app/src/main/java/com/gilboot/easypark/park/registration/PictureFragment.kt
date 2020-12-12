package com.gilboot.easypark.park.registration


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.PICK_PHOTO_REQUEST_CODE
import com.gilboot.easypark.PictureAdapter
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragPictureRegBinding
import com.gilboot.easypark.util.isNetworkAvailable
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import java.io.FileNotFoundException
import java.io.InputStream


class PictureFragment : Fragment() {
    private val parkViewModel: ParkViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragPictureRegBinding>(
                inflater,
                R.layout.frag_picture_reg,
                container,
                false
            )


        binding.parkViewModel = parkViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            pictureList.adapter = PictureAdapter(pictureOnClickListener)

        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.park_reg_pic_menu, menu)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                data?.let { intent ->
                    val inputStream: InputStream? =
                        context?.contentResolver?.openInputStream(intent.data!!)
                    inputStream?.let { stream ->
                        if (!requireContext().isNetworkAvailable()) {
                            longToast("Network error")
                        } else {
                            toast("HEH")
                            parkViewModel.addPicture(stream) { err: String ->
                                toast(err)
                                Timber.e("Encountered error")
                            }
                        }
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                startImagePicker()
            }

            R.id.next -> {
//                if (parkViewModel.parkLiveData.value!!.pictures.isEmpty()) {
//                    toast("You must add atleast one picture")
//                } else navigateToLocationFragment()
            }

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}

fun PictureFragment.navigateToLocationFragment() {
//    val action = PictureFragmentDirections.actionPictureFragmentToLocationFragment()
//    findNavController().navigate(action)
}


fun PictureFragment.startImagePicker() {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "image/*"
    startActivityForResult(intent, PICK_PHOTO_REQUEST_CODE)
}

val PictureFragment.pictureOnClickListener: PictureAdapter.OnClickListener
    get() = PictureAdapter.OnClickListener {
        toast("Link is $it")
    }
