package com.example.arearius

import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.arearius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // app list 화면으로 이동
        binding.btnApplist.setOnClickListener {

            //다음화면으로 이동하기 위한 인텐트 객체 생성
            val intent = Intent(this, AppAllListActivity::class.java)

            //HelloWorld라는 텍스트 값을 담음
            startActivity(intent)   //intent에 저장되어 있는 엑티비티 쪽으로 이동한다
            finish() //자기 자신 액티비티 파괴

        }
    }
}

