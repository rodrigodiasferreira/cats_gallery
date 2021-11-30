package br.org.venturus.example.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.org.venturus.example.model.imgur.Image
import br.org.venturus.example.repository.ImageRepository

class GalleryViewModel(private val repository: ImageRepository) : ViewModel() {

    fun retrieveAllFromDB(): LiveData<List<Image>> = repository.retrieveAllFromInternalDB()

    fun updateCatsImagesDBFromWeb(
        onSuccess: () -> Unit,
        onFailure: (error: String?) -> Unit
    ) = repository.updateDBFromWeb(onSuccess, onFailure)

    fun deleteAllImagesFromDB() = repository.deleteAllImagesFromDB()

}
