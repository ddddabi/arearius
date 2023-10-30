package com.example.arearius.interfaces

import retrofit2.Call
import com.example.arearius.data.FileAnalysisData
import retrofit2.http.*


interface FileApiService{
    // Get a file report
    @GET("api/v3/files/{id}")
    fun postData(
        @Path("id") fileId: String,
        @Header("x-apikey") apiKey: String
    ): Call<FileAnalysisData>
}

