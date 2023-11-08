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
        setSupportActionBar(binding.toolbar)	//툴바 사용 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)	//왼쪽 버튼 사용설정(기본은 뒤로가기)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.round_arrow_back_ios_new_24)	//왼쪽 버튼 메뉴로 아이콘 변경
        supportActionBar!!.setDisplayShowTitleEnabled(true)		//타이틀 보이게 설정


        /*binding.recycler01.layoutManager = LinearLayoutManager(this)
        binding.recycler01.adapter = listAdapter // RecyclerView에 어댑터 설정*/
        fileInitList()
        //Thread.sleep(30000) // 1분 대기 60000
        Toast.makeText(applicationContext, "스캔 시작 1분 대기", Toast.LENGTH_LONG).show()

        // 어댑터 초기화
        listAdapter = MyAdapter()
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
    // 툴바 메뉴 버튼을 설정- menu에 있는 item을 연결하는 부분
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.template_toolbar_menu, menu)       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return true
    }
    //Toolbar 메뉴 클릭 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { //뒤로 가기 버튼
                val intent = Intent(this, ListDetailActivity::class.java)
                startActivity(intent)
            }
            R.id.toolbar_info -> {// 툴팁
                // 메뉴 창으로 가도록
                //다음화면으로 이동하기 위한 인텐트 객체 생성
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)   //intent에 저장되어 있는 엑티비티 쪽으로 이동한다
            }
        }
        return super.onOptionsItemSelected(item)
    }
}





