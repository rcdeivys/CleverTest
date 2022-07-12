package com.rcdeivys.clevertest.common

import android.content.Context
import android.os.IBinder
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rcdeivys.clevertest.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> CoroutineScope.collectFlow(flow: Flow<T>, body: suspend (T) -> Unit) {
    flow.onEach { body(it) }
        .launchIn(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show(show: Boolean) {
    if (show) {
        show()
    } else {
        hide()
    }
}

fun ImageView.loadImageFromURL(
    context: Context,
    imageUrl: String
) {
    val options: RequestOptions = RequestOptions()
        .centerInside()
        .placeholder(R.color.primary_dark)
        .error(R.color.primary_dark)
    Glide.with(context)
        .load(imageUrl)
        .dontAnimate()
        .apply(options)
        .into(this)
}

fun ImageView.loadImageCircleFromURL(
    context: Context,
    imageUrl: String
) {
    val options: RequestOptions = RequestOptions()
        .centerInside()
        .placeholder(R.color.primary_dark)
        .error(R.color.primary_dark)
        .circleCrop()
    Glide.with(context)
        .load(imageUrl)
        .dontAnimate()
        .apply(options)
        .into(this)
}

fun showToastShort(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun hideKeyboard(context: Context, windowToken: IBinder) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun logError(message: String) {
    Log.e("DR:", "message: $message")
}

fun String?.orUnknown(context: Context) = this ?: context.getString(R.string.unknown)
