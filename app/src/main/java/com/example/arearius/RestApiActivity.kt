package com.example.arearius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class RestApiActivity : AppCompatActivity() {

    // 객체 생성
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.virustotal.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val fileApiService = retrofit.create(FileApiService::class.java)
    // 수정 필요 -> SHA256 값
    val id = "7e876b68b2daab2eb3641d348de32f15"
    val myapiKey = "71dc70f9b22a6069d44e4481072fcb5b210ed428d67ae915da4668d06ce77a52"

    // ApiData.kt 연결
    val FilePostresponse = fileApiService.postData(id,myapiKey)

    private lateinit var binding: ActivityRestApiBinding
    lateinit var listAdapter: MyAdapter
    var fileList = listOf<FileAnalysisData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 어댑터 초기화
        listAdapter = MyAdapter()

        /*binding.recycler01.layoutManager = LinearLayoutManager(this)
        binding.recycler01.adapter = listAdapter // RecyclerView에 어댑터 설정*/
        fileInitList()
        //Thread.sleep(30000) // 1분 대기 60000
        Toast.makeText(applicationContext, "스캔 시작 1분 대기", Toast.LENGTH_LONG).show()


        binding.recycler01.apply {
            binding.recycler01.layoutManager = LinearLayoutManager(context)
            binding.recycler01.adapter = listAdapter // RecyclerView에 어댑터 설정
            //adapter = listAdapter
            //layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun fileInitList() {
        FilePostresponse.enqueue(object : Callback<FileAnalysisData> {
            override fun onResponse(call: Call<FileAnalysisData>, response: Response<FileAnalysisData>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        Log.d("OK", data.toString())
                        fileList = listOf(data) // 성공한 경우 fileList 업데이트
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





