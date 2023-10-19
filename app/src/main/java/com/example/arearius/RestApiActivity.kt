package com.example.arearius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arearius.adapter.MyAdapter
import com.example.arearius.data.AnalysisData
import com.example.arearius.databinding.ActivityRestApiBinding
import com.example.arearius.interfaces.DataApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApiActivity : AppCompatActivity() {

    // 객체 생성
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.virustotal.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val dataApiService = retrofit.create(DataApiService::class.java)
    // 수정 필요 -> SHA256 값
    val urlId = "6a850be56515cfc1ffbcc365ff650e601a737353dd267d43713ae7583019721a"
    val myapiKey = "71dc70f9b22a6069d44e4481072fcb5b210ed428d67ae915da4668d06ce77a52"
    val URLPostresponse = dataApiService.postData(urlId, myapiKey)

    private lateinit var binding: ActivityRestApiBinding
    lateinit var listAdapter: MyAdapter
    var coinList = listOf<AnalysisData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = MyAdapter()

        binding.btnApi.setOnClickListener {
            initList()
            //Thread.sleep(30000) // 1분 대기 60000
            Toast.makeText(applicationContext, "스캔 시작 1분 대기", Toast.LENGTH_LONG).show()
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
        URLPostresponse.enqueue(object : Callback<AnalysisData> {
            override fun onResponse(call: Call<AnalysisData>, response: Response<AnalysisData>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("OK", it.toString())
                        val result = response.body()?.toString()
                        Log.d("OK", result.toString())
                        coinList = listOf(it) // 성공한 경우 coinList 업데이트
                        listAdapter.setList(coinList)
                    }
                } else {
                    Log.d("ERROR", response.toString())
                    Toast.makeText(applicationContext, "API 호출 실패", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AnalysisData>, t: Throwable) {
                Log.d("ERROR", t.toString())
                Toast.makeText(applicationContext, "API 호출 실패2", Toast.LENGTH_LONG).show()
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
            }


        })
    }
}


