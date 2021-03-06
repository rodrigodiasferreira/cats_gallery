package br.org.venturus.example.di


import androidx.room.Room
import br.org.venturus.example.database.AppDatabase
import br.org.venturus.example.repository.ImageRepository
import br.org.venturus.example.retrofit.webclient.ImgurWebClient
import br.org.venturus.example.ui.recyclerview.adapter.GalleryAdapter
import br.org.venturus.example.ui.viewmodel.AppStateViewModel
import br.org.venturus.example.ui.viewmodel.GalleryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATABASE_NAME = "gallery.db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}

val daoModule = module {
    single { get<AppDatabase>().imageDao() }
}

val adapterModule = module {
    factory { GalleryAdapter(get()) }
}

private val repositoryModule = module {
    single { ImageRepository(get(), get(), get()) }
}

private val webServiceModule = module {
    single { ImgurWebClient(get()) }
}

private val viewModelModule = module {
    viewModel { AppStateViewModel() }
    viewModel { GalleryViewModel(get()) }
}

val modules = listOf(
    databaseModule,
    repositoryModule,
    viewModelModule,
    adapterModule,
    daoModule,
    webServiceModule
)