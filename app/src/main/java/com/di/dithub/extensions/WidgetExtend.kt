package com.di.dithub.extensions

import android.view.View
import android.widget.EditText

fun EditText.trimText(): String {
    return this.text.toString().trim()
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}