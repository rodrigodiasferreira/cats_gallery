package br.org.venturus.example.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showMessage(message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()
}