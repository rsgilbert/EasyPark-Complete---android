package com.gilboot.easypark.reserve

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragReserveBinding
import com.gilboot.easypark.util.longSnackbar
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ReserveFragment : Fragment() {
    val reserveViewModel: ReserveViewModel by viewModels {
        ReserveViewModelFactory(
            reserveFromArgs
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<FragReserveBinding>(
                inflater,
                R.layout.frag_reserve,
                container,
                false
            )

        binding.reserveViewModel = reserveViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.reservation_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareQr()
            R.id.download -> downloadQr()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}

// Share QR code to other people so they can use it
fun ReserveFragment.shareQr() {
    val uri = saveImageToExternalPrivate(reserveViewModel.bitMap!!)
    if (uri == null) longSnackbar("Failed to save qr code")
    uri?.let { shareImageUri(uri) }
}


// Save the QR code as an image
fun ReserveFragment.downloadQr() {
    MediaStore.Images.Media.insertImage(
        requireActivity().contentResolver,
        reserveViewModel.bitMap,
        reserveViewModel.reserve.parkName,
        null
    );
    longSnackbar("Downloaded Qr")
}

val ReserveFragment.reserveFromArgs
    get() = ReserveFragmentArgs.fromBundle(arguments!!).reserve


/**
 * Saves the image as PNG to the app's private external storage folder.
 * @param image Bitmap to save.
 * @return Uri of the saved file or null
 */
fun Fragment.saveImageToExternalPrivate(image: Bitmap): Uri? {
    //TODO - Should be processed in another thread
    var uri: Uri? = null
    try {
        val file =
            File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "to-share.png"
            )
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.close()
        uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            file
        )
    } catch (e: IOException) {
        Timber.e("IOException while trying to write file for sharing: ${e.message}")
    }
    return uri
}

/**
 * Shares the PNG image from Uri.
 * @param uri Uri of image to share.
 */
fun Fragment.shareImageUri(uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "image/png"
    startActivity(intent)
}