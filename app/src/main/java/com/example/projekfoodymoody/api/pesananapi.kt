package com.example.projekfoodymoody.api

import com.example.projekfoodymoody.api.data.pesanandata
import retrofit2.Response
import retrofit2.http.*

public interface pesananapi {
    @GET("/rest/v1/pesanan?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<pesanandata>>

    @POST("/rest/v1/pesanan")
    suspend fun create(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body todoData: pesanandata
    )

    @DELETE("/rest/v1/pesanan")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("id") idQuery : String
    ) : Response<Unit>
}