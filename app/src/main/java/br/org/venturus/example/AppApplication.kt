package br.org.venturus.example

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import br.org.venturus.example.di.modules

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@AppApplication)
            modules(modules)
        }
    }
}