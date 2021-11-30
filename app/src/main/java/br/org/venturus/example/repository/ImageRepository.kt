package br.org.venturus.example.repository

import android.content.Context
import androidx.lifecycle.LiveData
import br.org.venturus.example.R
import br.org.venturus.example.database.dao.ImageDAO
import br.org.venturus.example.model.imgur.Image
import br.org.venturus.example.model.imgur.ImgurRootData
import br.org.venturus.example.retrofit.webclient.ImgurWebClient
import br.org.venturus.example.utils.IMAGE
import br.org.venturus.example.utils.NetworkHelper.Companion.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageRepository(
    private val context: Context,
    private val dao: ImageDAO,
    private val webclient: ImgurWebClient
) {

    fun deleteAllImagesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllImages()
        }
    }

    fun retrieveAllFromInternalDB(): LiveData<List<Image>> {
        return dao.retrieveAll()
    }

    fun updateDBFromWeb(
        onSuccess: () -> Unit,
        onFailure: (error: String?) -> Unit
    ) {
        if (isNetworkAvailable(context)) {
            webclient.retrieveCats(
                onSuccess = { body ->
                    body?.let {
                        /**
                         * Here it could be used a lot of different strategies of caching data
                         * from Web into DB
                         * As this is only a test app, so I am simply checking in order to add
                         * into DB the new entries from the Web
                         * There are much better strategies depending on the WebService that you
                         * are consuming, link only retrieve from the Web the changed entries
                         * and update the diff into the database
                         * Or even if there is some NoSQL DB (e.g. Cloud Firestore) which notifies
                         * the subscribed observer to that level of hierarchy of the data, in this
                         * case it would not even needed a local database, as it already support
                         * offline operations, and later when the conection is re-established
                         * it syncs with the cloud.
                         * */

                        if (body.success && body.status == 200) {

                            CoroutineScope(Dispatchers.IO).launch {
                                val dbImages = dao.retrieveAllSychronously()
                                val imagesList: MutableList<Image> = filteringOnlyImages(body)
                                val dbLinksList: MutableList<String> = mutableListOf()
                                buildListOfLinksFromDB(dbImages, dbLinksList)
                                removingRepeatedEntriesInDBRetrievedFromWeb(imagesList, dbLinksList)
                                saveIntoInternalDB(imagesList)
                            }
                        } else {
                            onFailure(context.getString(R.string.error_retrieveing_data))
                        }
                    }
                    onSuccess()
                },
                onFailure = onFailure
            )
        } else {
            onFailure(context.getString(R.string.network_unavailable))
        }
    }

    private fun saveIntoInternalDB(
        images: List<Image>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.save(images)
        }
    }

    private fun removingRepeatedEntriesInDBRetrievedFromWeb(
        imagesList: MutableList<Image>,
        dbLinksList: MutableList<String>
    ) {
        for (i in imagesList.size - 1 downTo 0) {
            if (dbLinksList.contains(imagesList[i].link)) {
                imagesList.removeAt(i)
            }
        }
    }

    private fun buildListOfLinksFromDB(
        dbImages: List<Image>,
        dbLinksList: MutableList<String>
    ) {
        dbImages.forEach { image ->
            dbLinksList.add(image.link)
        }
    }

    //As the requirement says to show images, filtering only images (videos are filtered out)
    private fun filteringOnlyImages(body: ImgurRootData): MutableList<Image> {
        val imagesList: MutableList<Image> = mutableListOf()
        body.data.forEach { imgurItem ->
            imgurItem.images.forEach { image ->
                filterImages(image, imagesList)
            }
        }
        return imagesList
    }

    private fun filterImages(
        image: Image,
        imagesList: MutableList<Image>
    ) {
        if (image.type.contains(IMAGE)) {
            imagesList.add(image)
        }
    }

}
