package com.example.restaurant.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(text: String) {
    activity?.let {
        Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
    }
}