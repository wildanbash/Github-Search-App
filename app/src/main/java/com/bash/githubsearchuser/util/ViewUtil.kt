package com.bash.githubsearchuser.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.View
import com.bash.githubsearchuser.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun String.toBitmap(context: Context): Bitmap {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_picture)

    val option = RequestOptions()
        .error(R.drawable.img_picture)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    try {
        Glide.with(context)
            .setDefaultRequestOptions(option)
            .asBitmap()
            .load(this)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                }
            })
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return bitmap
}