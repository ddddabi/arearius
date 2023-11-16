package com.example.arearius.interfaces

import retrofit2.Call
import com.example.arearius.data.FileAnalysisData
import com.example.arearius.data.ScanResult
import retrofit2.http.*


interface FileApiService{
    // Get a file report
    @GET("api/v3/files/{id}")
    fun postData(
        @Path("id") fileId: String,
        @Header("x-apikey") apiKey: String
    ): Call<FileAnalysisData>
}

interface MetaApiDataHash{
    // Retrieving scan reports using a data hash
    @GET("hash/{hash}")
    fun GetData(
        @Path("hash") hash: String,
        @Header("apikey") apiKey: String
    ): Call<ScanResult>
}

