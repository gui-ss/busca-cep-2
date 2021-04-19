package com.stefanini.buscacep.datasource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HTTPClient {

        companion object {
            @JvmStatic
            fun retrofit(): Retrofit {
                 return Retrofit.Builder()
                .baseUrl(CepAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            }
        }
}