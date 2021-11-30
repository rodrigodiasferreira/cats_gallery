package br.org.venturus.example.retrofit.webclient

import android.content.Context
import br.org.venturus.example.R
import br.org.venturus.example.model.imgur.ImgurRootData
import br.org.venturus.example.retrofit.AppRetrofit
import br.org.venturus.example.retrofit.service.ImgurService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImgurWebClient(
    private val context: Context,
    private val service: ImgurService = AppRetrofit().imgurService
) {

    private fun <T> requestExecution(
        call: Call<T>,
        onSuccess: (newImages: T?) -> Unit,
        onFailure: (error: String?) -> Unit
    ) {
        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onFailure(context.getString(R.string.failure_on_request))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure(t.message)
            }
        })
    }

    fun retrieveCats(
        onSuccess: (imagurRootData: ImgurRootData?) -> Unit,
        onFailure: (error: String?) -> Unit
    ) {
        requestExecution(
            service.retrieveCats(),
            onSuccess,
            onFailure
        )
    }

}
