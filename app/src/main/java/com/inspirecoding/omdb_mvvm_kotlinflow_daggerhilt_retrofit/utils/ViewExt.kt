package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.utils

import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}
fun View.inVisible() {
    this.visibility = View.INVISIBLE
}
fun View.gone() {
    this.visibility = View.GONE
}