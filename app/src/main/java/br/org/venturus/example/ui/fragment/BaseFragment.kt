package br.org.venturus.example.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * This Base fragment can be used for common behaviors
 * e.g.: control if the use is logged it, other redirect to login screen
 *  or defining common options menu for the application (e.g. sign out)
 *  therefore I already implemented and
 *  the other fragments will extend from this one
 *  Right now it is not adding any additional behavior, as it was not needed
 * */

abstract class BaseFragment : Fragment() {

    // Not used, but added thinking on app evolution
    // like some authentication to access content
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}