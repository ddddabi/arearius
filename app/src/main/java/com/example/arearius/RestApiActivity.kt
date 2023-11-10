package com.example.arearius

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arearius.adapter.MyAdapter
import com.example.arearius.data.FileAnalysisData
import com.example.arearius.databinding.ActivityRestApiBinding
import com.example.arearius.interfaces.FileApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RestApiActivity : AppCompatActivity() {

    // 객체 생성
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.virustotal.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val fileApiService = retrofit.create(FileApiService::class.java)

    val myapiKey = "71dc70f9b22a6069d44e4481072fcb5b210ed428d67ae915da4668d06ce77a52"

    private lateinit var binding: ActivityRestApiBinding
    lateinit var listAdapter: MyAdapter
    var fileList = listOf<FileAnalysisData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestApiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            delay(30000) // 30초 대기

            loadData(intent.getStringExtra("md5").toString())
            binding.loadingtxt.visibility = View.GONE
        }

        // 어댑터 초기화
        listAdapter = MyAdapter()
        binding.recycler01.layoutManager = LinearLayoutManager(this)
        binding.recycler01.adapter = listAdapter

        // home 버튼
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // app list 버튼
        binding.btnApplist.setOnClickListener {
            val intent = Intent(this, AppAllListActivity::class.java)
            startActivity(intent)
        }

    }
    private fun loadData(id : String) {
        fileApiService.postData(id, myapiKey).enqueue(object : Callback<FileAnalysisData> {
            override fun onResponse(call: Call<FileAnalysisData>, response: Response<FileAnalysisData>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        Log.d("OK", data.toString())
                        fileList = listOf(data)
                        listAdapter.setList(fileList)
                    }
                } else {
                    Log.d("ERROR", response.toString())
                    Toast.makeText(applicationContext, "API 호출 실패", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<FileAnalysisData>, t: Throwable) {
                Log.d("ERROR", t.toString())
                Toast.makeText(applicationContext, "API 호출 실패2", Toast.LENGTH_LONG).show()
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}





