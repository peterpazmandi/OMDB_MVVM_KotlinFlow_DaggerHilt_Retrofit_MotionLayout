package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.utils

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.dismissKeyboard(view : View?) {

    view?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }

}

fun Context.fromHtmlWithParams(@StringRes stringRes: Int, parameter : String? = null) : Spanned {

    val stringText = if (parameter.isNullOrEmpty()) {
                        this.getString(stringRes)
                    } else {
                        this.getString(stringRes, parameter)
                    }

    return Html.fromHtml(stringText, Html.FROM_HTML_MODE_LEGACY)

}

fun Context.showToast(message : String) {

    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}