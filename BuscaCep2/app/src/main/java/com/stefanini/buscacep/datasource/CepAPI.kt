package com.stefanini.buscacep.datasource

import com.stefanini.buscacep.model.Cep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepAPI {
    companion object{
        const val BASE_URL: String = "http://viacep.com.br/"
    }

    @GET("ws/{cep}/json/")
    fun findCep(@Path("cep") cep: String?): Call<Cep>

}