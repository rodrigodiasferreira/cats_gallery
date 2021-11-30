package br.org.venturus.example.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import br.org.venturus.example.R
import br.org.venturus.example.databinding.ActivityMainBinding
import br.org.venturus.example.ui.viewmodel.AppStateViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val appStateViewModel: AppStateViewModel by viewModel()
    private val navController by lazy { findNavController(R.id.nav_host) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            configureVisualComponents(destination)
        }
    }

    private fun configureVisualComponents(destination: NavDestination) {
        //If on the AppStateViewModel one decides to show the visual component appbar
        //the title will already be define according to label defined on nav_graph.xml
        title = destination.label
        appStateViewModel.liveComponents.observe(this, Observer { visualComponents ->
            visualComponents?.let {
                if (it.appBar) {
                    supportActionBar?.show()
                } else {
                    supportActionBar?.hide()
                }
            }
        })
    }
}