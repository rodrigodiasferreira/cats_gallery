package br.org.venturus.example.retrofit.service

import br.org.venturus.example.model.imgur.ImgurRootData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImgurService {

    @Headers("Authorization: Client-ID 1ceddedc03a5d71")
    @GET("gallery/search/?q=cats")
    fun retrieveCats(): Call<ImgurRootData>

}
