package br.org.venturus.example.retrofit

import br.org.venturus.example.BuildConfig
import br.org.venturus.example.retrofit.service.ImgurService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.imgur.com/3/"

class AppRetrofit {

    private val client by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
    private val retrofit by lazy {
        val retrofitBuider = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
        if (BuildConfig.DEBUG){
            retrofitBuider.client(client)
        }
        retrofitBuider.build()
    }
    val imgurService: ImgurService by lazy {
        retrofit.create(ImgurService::class.java)
    }

}