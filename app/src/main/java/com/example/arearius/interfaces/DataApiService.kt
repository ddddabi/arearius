package com.example.arearius.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.arearius.data.ApiData

interface DataApiService {
    @GET("/api/v2/publicholidays/{year}/{locale}")
    fun getHolidays(
        @Path("year") year: String,
        @Path("locale") locale: String
    ) : Call <List<ApiData>>
}