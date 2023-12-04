package com.example.arearius

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arearius.adapter.MetaListAdapter
import com.example.arearius.data.AppData
import com.example.arearius.data.ScanResult
import com.example.arearius.databinding.ActivityMetaApiBinding
import com.example.arearius.interfaces.MetaApiDataHash
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MetaApiActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMetaApiBinding
    lateinit var listAdapter:MetaListAdapter
    var datalist = listOf<ScanResult>()
    private lateinit var packI: PackageInfo

    // retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.metadefender.com/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dataHashScan = retrofit.create(MetaApiDataHash::class.java)
    val myapikey = "f862021e371580ee14f2267b15754ffc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMetaApiBinding.inflate(layoutInflater)
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
        listAdapter = MetaListAdapter()
        binding.recycler01.layoutManager = LinearLayoutManager(this)
        binding.recycler01.adapter = listAdapter

        // setting 버튼
        binding.btnSetting.setOnClickListener {
            val packageName = packI.packageName
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
            startActivity(intent)
        }
        // home 버튼
        binding.btnhome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // app list 버튼
        binding.btnApplist.setOnClickListener {
            val intent = Intent(this, AppAllListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadData(hash: String) {
        dataHashScan.GetData(hash, myapikey).enqueue(object : Callback<ScanResult> {
            override fun onResponse(call: Call<ScanResult>, response: Response<ScanResult>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        Log.d("OK", data.toString())
                        datalist = listOf(data)
                        listAdapter.setList(datalist)
                    }
                } else {
                    Log.d("ERROR", response.toString())
                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ScanResult>, t: Throwable) {
                Log.e("FAILURE", "API 호출 실패", t)
                Toast.makeText(applicationContext, "API 호출 실패", Toast.LENGTH_LONG).show()
            }
        })
    }

}
