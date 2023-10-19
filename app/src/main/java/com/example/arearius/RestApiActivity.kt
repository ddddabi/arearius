package com.example.arearius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arearius.adapter.MyAdapter
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
        .baseUrl("https://api.upbit.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val dataApiService = retrofit.create(DataApiService::class.java)

    private lateinit var binding: ActivityRestApiBinding
    lateinit var listAdapter: MyAdapter
    var coinList = listOf<ApiData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = MyAdapter()

        binding.btnApi.setOnClickListener {
            initList()
        }

        binding.recycler01.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        // API 데이터 초기 로딩
        initList()
    }

    private fun initList() {
        dataApiService.getCoinAll()
            .enqueue(object: Callback<List<ApiData>>{
                override fun onResponse(
                    call: Call<List<ApiData>>,
                    response: Response<List<ApiData>>
                ) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            Log.d("OK", it.toString())
                            coinList = it // 성공한 경우 coinList 업데이트
                            listAdapter.setList(coinList)
                        }
                    }else {
                        Log.d("ERROR", response.toString())
                        Toast.makeText(applicationContext, "API 호출 실패", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<ApiData>>, t: Throwable) {
                    Log.d("ERROR",t.toString())
                    Toast.makeText(applicationContext, t.toString(),Toast.LENGTH_LONG).show()
                }
            })
    }
}