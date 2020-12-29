package com.gilboot.easypark

import android.graphics.Bitmap
import android.media.Image
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.reservelist.ReserveListAdapter
import com.gilboot.easypark.util.QrSize
import com.gilboot.easypark.util.setQrCode
import de.hdodenhof.circleimageview.CircleImageView


// Bind picture list to recyclerview
// using the picture adapter
@BindingAdapter("park")
fun RecyclerView.bindPark(park: Park?) {
    park?.let {
//        (adapter as PictureAdapter).submitList(it.pictures)
    }
}

// set image url to image view
@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

// set image url to image view
@BindingAdapter("imageUrl")
fun CircleImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}


@BindingAdapter("addDivider")
fun RecyclerView.addDivider(shouldAdd: Boolean?) =
    shouldAdd?.let {
        if (it) {
            val itemDec = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDec.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
            addItemDecoration(itemDec)
        }
    }


// adapter for reserveList
@BindingAdapter("reserveList")
fun RecyclerView.bindReserveList(reserveList: List<Visit>?) {
    reserveList?.let {
        (adapter as ReserveListAdapter).submitList(it)
    }
}


// setting qr code to image view
@BindingAdapter("setSmallQr")
fun ImageView.bindSmallQr(reserve: Visit?) {
    reserve?.let {
        setQrCode(it, QrSize.SMALL)
    }
}

// setting qr code to image view
@BindingAdapter("setLargeQr")
fun ImageView.bindLargeQr(reserve: Visit?) {
    reserve?.let {
        setQrCode(it, QrSize.LARGE)
    }
}

@BindingAdapter("setBitmap")
fun ImageView.bindBitmap(bitmap: Bitmap?) {
    bitmap?.let { setImageBitmap(bitmap) }
}

