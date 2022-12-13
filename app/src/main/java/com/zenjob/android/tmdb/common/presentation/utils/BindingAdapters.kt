package com.zenjob.android.tmdb.common.presentation.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, url: String?) {
    url ?: return
    Glide.with(imageView.context).load(url).centerCrop().into(imageView)
}

@BindingAdapter("visibleIf")
fun bindVisibleIf(view: View, condition: Boolean) {
    view.visibility = if (condition) {
        View.VISIBLE
    } else {
        View.GONE
    }
}