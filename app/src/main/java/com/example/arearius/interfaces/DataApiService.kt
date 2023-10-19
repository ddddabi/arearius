package com.example.arearius.interfaces

import retrofit2.Call
import com.example.arearius.data.AnalysisData
import okhttp3.RequestBody
import retrofit2.http.*

interface DataApiService {
    // Get a URL analysis report
    @GET("api/v3/urls/{urlId}")
    fun postData(
        @Path("urlId") urlId: String,
        @Header("x-apikey") apiKey: String
    ): Call<AnalysisData>
}