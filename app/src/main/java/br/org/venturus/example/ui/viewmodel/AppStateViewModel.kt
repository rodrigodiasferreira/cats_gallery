package br.org.venturus.example.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Common ViewModel in order to configure for the different
 * fragments configure the visual components that they may have
 * on this app example I added the app title bar component
 * */
class AppStateViewModel : ViewModel() {

    val liveComponents: LiveData<VisualComponents> get() = _components
    private val _components: MutableLiveData<VisualComponents> =
        MutableLiveData<VisualComponents>().also {
            it.value = components
        }

    var components: VisualComponents = VisualComponents()
        set(value) {
            field = value
            _components.value = value
        }

}

class VisualComponents(
    val appBar: Boolean = false
)