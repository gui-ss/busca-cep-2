package com.stefanini.buscacep.datasource

import android.annotation.SuppressLint
import com.stefanini.buscacep.MainActivity
import com.stefanini.buscacep.model.Cep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CepRemoteDataSource {

    interface CepCallback{
        fun onSucess(response: Cep?)

        fun onError(message: String?)

        fun onComplete()
    }

    fun findCepBy(callback: CepCallback, cep: String){
        HTTPClient.retrofit().create(CepAPI::class.java)
            .findCep(cep = cep)
            .enqueue(object : Callback<Cep> {
                override fun onFailure(call: Call<Cep>, t: Throwable) {
                    callback.onError(t.message)
                    callback.onComplete()
                }

                override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
                    if (response.isSuccessful)
                        callback.onSucess(response.body())

                    callback.onComplete()
                }

            })
    }



}