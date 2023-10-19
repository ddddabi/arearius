package com.example.arearius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.arearius.data.ApiData
import com.example.arearius.databinding.ActivityRestApiBinding
import com.example.arearius.interfaces.DataApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApiActivity : AppCompatActivity() {

    // 객체 생성
    val retrofit = Retrofit.Builder()
        .baseUrl("https://date.nager.at/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val dataApiService = retrofit.create(DataApiService::class.java)

    private lateinit var binding: ActivityRestApiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnApi.setOnClickListener {
            dataApiService.getHolidays("2023","KR")
                .enqueue(object: Callback<List<ApiData>>{
                    override fun onResponse(
                        call: Call<List<ApiData>>,
                        response: Response<List<ApiData>>
                    ) {
                        if (response.isSuccessful.not()){
                            return
                        }
                        response.body()?.let {
                            Log.d("OK",it.toString())
                            it.forEachIndexed { index, apiData ->
                                Log.d("DATA","[$index] date = ${apiData.date}, name = ${apiData.dateName}")
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<ApiData>>, t: Throwable) {
                        Log.d("ERROR",t.toString())
                        Toast.makeText(applicationContext, t.toString(),Toast.LENGTH_LONG).show()
                    }
                })
        }



    }
}