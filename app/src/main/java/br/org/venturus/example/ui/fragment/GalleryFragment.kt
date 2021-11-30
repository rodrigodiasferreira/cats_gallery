package br.org.venturus.example.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.org.venturus.example.R
import br.org.venturus.example.databinding.GalleryBinding
import br.org.venturus.example.extensions.showMessage
import br.org.venturus.example.ui.recyclerview.adapter.GalleryAdapter
import br.org.venturus.example.ui.viewmodel.AppStateViewModel
import br.org.venturus.example.ui.viewmodel.GalleryViewModel
import br.org.venturus.example.ui.viewmodel.VisualComponents
import br.org.venturus.example.utils.SPHelper.getBooleanFromSP
import br.org.venturus.example.utils.SPHelper.putBooleanIntoSP
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class GalleryFragment : BaseFragment() {

    private val viewModel: GalleryViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private val adapter: GalleryAdapter by inject()
    private val alertDialog by lazy { createCleanDBConfirmationAlertDialog() }
    //Not used bug already inserted for future evolution
    private val navController by lazy { findNavController() }
    private var _binding: GalleryBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GalleryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // The automatic sync is done only the first time the app is opened
        // afterwards it is needed to swipe down to update the data (which only adds new entries)
        // repeated ones are ignored
        firstSyncFromWebIntoDB()
        retrieveAllFromDB()
        // I've enabled the app bar in order to insert the functionality of deleting the caching db
        // I know it wasn't on the image from the exercise requirement, but I took the initiative to add,
        // because I considered it would be a nice feature, otherwise the cache DB would only increase
        // in size, and it would be needed to use the app's storage cleaning on settings in order
        // to reset the cache db
        appStateViewModel.components = VisualComponents(appBar = true)
        configureRecyclerView()
        configureSwipe()
    }

    override fun onResume() {
        super.onResume()
        configureRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.clean_cache_db){
            alertDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun retrieveAllFromDB() {
        viewModel.retrieveAllFromDB().observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                adapter.update(it)
            }
        })
    }

    private fun createCleanDBConfirmationAlertDialog(): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.clean_db_confirmation))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteAllImagesFromDB()
                showMessage(getString(R.string.cache_db_cleaned_successfully))
                dismissDialog()
                updateCatsImagesDBFromWeb()
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                dismissDialog()
            }
            .create()
    }

    private fun dismissDialog() {
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

    private fun configureSwipe() {
        viewBinding.swipe.setOnRefreshListener {
            updateCatsImagesDBFromWeb()
        }
    }

    private fun firstSyncFromWebIntoDB() {
        if (!getBooleanFromSP(requireContext(), R.string.synced_once)) {
            viewBinding.swipe.isRefreshing = true
            updateCatsImagesDBFromWeb()
        }
    }

    private fun updateCatsImagesDBFromWeb() {
        viewModel.updateCatsImagesDBFromWeb(
            onSuccess = {
                viewBinding.swipe.isRefreshing = false
                putBooleanIntoSP(requireContext(), R.string.synced_once, true)
            },
            onFailure = { onFailure ->
                onFailure?.let {
                    showMessage(onFailure)
                }
                viewBinding.swipe.isRefreshing = false
            }
        )
    }

    private fun configureRecyclerView() {
        adapter.onItemClickListener = {
//                selectedImage ->
            // Click on image code goes here e.g. navigate to some
            // image fragments details and send data from the selected image
            // navController.navigate()...
        }
        configureRecyclerViewUIComponents()
    }

    private fun configureRecyclerViewUIComponents() {
        adapter.recyclerView = viewBinding.galleryRecyclerview
        viewBinding.galleryRecyclerview.adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        viewBinding.galleryRecyclerview.adapter = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
