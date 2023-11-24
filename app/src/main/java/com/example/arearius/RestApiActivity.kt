package com.example.arearius

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
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
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.arearius.data.AppData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RestApiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestApiBinding
    lateinit var listAdapter: MyAdapter
    var fileList = listOf<FileAnalysisData>()
    private lateinit var packI: PackageInfo

    // 객체 생성
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.virustotal.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val fileApiService = retrofit.create(FileApiService::class.java)
    val myapiKey = "71dc70f9b22a6069d44e4481072fcb5b210ed428d67ae915da4668d06ce77a52"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 패키지 이름 가져오기
        val appData = applicationContext as AppData
        packI = appData.packageInfo

        // 카운트 다운
        val totalTimeMillis = 10000 // 총 시간 (30초)
        val intervalMillis = 1000 // 업데이트 간격 (1초)

        val countDownTimer = object : CountDownTimer(totalTimeMillis.toLong(), intervalMillis.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                // progressbar
                val totalTimeSeconds = totalTimeMillis / 1000
                val secondsRemaining = millisUntilFinished / 1000
                val progressPercentage = ((totalTimeSeconds - secondsRemaining) / totalTimeSeconds.toFloat()) * 100

                binding.loadingBar.progress = progressPercentage.toInt()

                // TextView에 남은 시간 표시
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                val timeRemainingText = String.format("%02d:%02d", minutes, seconds)
                binding.timeRemainingText.text = timeRemainingText
            }

            override fun onFinish() {
                binding.loadinglayout.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
                binding.timeRemainingText.visibility =View.GONE
            }
        }

        lifecycleScope.launch {
            countDownTimer.start()

            // 대기 후 데이터 로드
            delay(totalTimeMillis.toLong())
            countDownTimer.cancel() // 타이머 취소

            loadData(intent.getStringExtra("md5").toString())
            binding.loadinglayout.visibility = View.GONE
            binding.loadingBar.visibility = View.GONE
            binding.timeRemainingText.visibility =View.GONE
        }

        // 어댑터 초기화
        listAdapter = MyAdapter()
        binding.recycler01.layoutManager = LinearLayoutManager(this)
        binding.recycler01.adapter = listAdapter

        // setting 버튼
        binding.btnSetting.setOnClickListener {
            val packageName = packI.packageName
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
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





