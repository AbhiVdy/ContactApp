package com.core.contactapp.ui

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.core.contactapp.R

@BindingAdapter("profilePictureUrl")
fun bindProfilePicture(imageView: ImageView, profilePictureUrl: String?) {

    profilePictureUrl?.let {
        val profilePictureUri = profilePictureUrl.toUri()

        Glide.with(imageView.context)
            .load(profilePictureUri)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}