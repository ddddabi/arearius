package com.example.arearius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.arearius.data.App
import com.example.arearius.databinding.ActivityListDetailBinding

class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_list_detail)
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // appInfo를 serializable로 받는다
        // 그냥 받은 채로 변수에 넣으면 오류가 나는데 이 때 Casting을 해줘야 한다
        val item = intent.getSerializableExtra("roomInfo") as App

        // activity_room_detail.xml에 설정했던 view에 따라 매핑
        binding.nameTxt.text = "${item.appName}"
        binding.idTxt.text = item.appId
        binding.packageTxt.text = item.appPackageName
        binding.versionTxt.text = item.appVersion
    }
}