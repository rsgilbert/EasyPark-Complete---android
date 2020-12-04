package com.gilboot.easypark

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gilboot.easypark.data.Park


// Bind picture list to recyclerview
// using the picture adapter
@BindingAdapter("park")
fun RecyclerView.bindPark(park: Park?) {
    park?.let {
        (adapter as PictureAdapter).submitList(it.pictures)
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