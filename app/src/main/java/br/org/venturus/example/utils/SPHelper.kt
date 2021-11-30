package br.org.venturus.example.utils

import android.content.Context
import br.org.venturus.example.R

object SPHelper {
    fun putBooleanIntoSP(context: Context, elementID: Int, content: Boolean) {
        val sharedPref =
            context.getSharedPreferences(context.getString(R.string.sp), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(context.getString(elementID), content)
        editor.apply()
    }

    fun getBooleanFromSP(context: Context, elementID: Int): Boolean {
        val sharedPref =
            context.getSharedPreferences(context.getString(R.string.sp), Context.MODE_PRIVATE)
        return sharedPref.getBoolean(context.getString(elementID), false)
    }
}