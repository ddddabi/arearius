package com.example.arearius

import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.arearius.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)	//툴바 사용 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)	//왼쪽 버튼 사용설정(기본은 뒤로가기)
        supportActionBar!!.setDisplayShowTitleEnabled(true)		//타이틀 보이게 설정

        // app list 화면으로 이동
        binding.btnApplist.setOnClickListener {

            //다음화면으로 이동하기 위한 인텐트 객체 생성
            val intent = Intent(this, AppAllListActivity::class.java)

            startActivity(intent)   //intent에 저장되어 있는 엑티비티 쪽으로 이동한다
            finish() //자기 자신 액티비티 파괴
        }
    }
    // 툴바 메뉴 버튼을 설정- menu에 있는 item을 연결하는 부분
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.template_toolbar_menu, menu)       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return true
    }
    //Toolbar 메뉴 클릭 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { //뒤로 가기 버튼
                finish()
            }
            R.id.toolbar_info -> {// 툴팁
                // 메뉴 창으로 가도록
                Snackbar.make(binding.toolbar,"Account Menu pressed",Snackbar.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

