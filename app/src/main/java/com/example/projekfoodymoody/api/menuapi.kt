package com.example.projekfoodymoody.api

import com.example.projekfoodymoody.api.data.menuitem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface menuapi {
    @GET("/rest/v1/menu?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<menuitem>>
}