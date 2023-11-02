package com.example.arearius

import android.content.Intent
import android.content.pm.FeatureInfo
import android.content.pm.PackageInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.arearius.data.AppData
import com.example.arearius.databinding.ActivityListDetailBinding
import java.text.SimpleDateFormat
import java.util.*


class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailBinding
    private lateinit var PI: PackageInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_list_detail)
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)	//툴바 사용 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)	//왼쪽 버튼 사용설정(기본은 뒤로가기)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.round_arrow_back_ios_new_24)	//왼쪽 버튼 메뉴로 아이콘 변경
        supportActionBar!!.setDisplayShowTitleEnabled(true)		//타이틀 보이게 설정

        val appData = applicationContext as AppData
        PI = appData.packageInfo

        setValues()

        // 버튼 클릭시 액티비티 이동
        binding.btntotal.setOnClickListener {
            val intent = Intent(this, RestApiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setValues() {
        // APP name
        binding.applabel.text = packageManager.getApplicationLabel(PI.applicationInfo)

        // package name
        binding.packageName.text = PI.packageName

        // version name
        binding.versionName.text = PI.versionName

        // target version
        binding.sdkversion.text = PI.applicationInfo.targetSdkVersion.toString()

        // path
        binding.path.text = PI.applicationInfo.sourceDir

        // first installation
        binding.insdate.text = setDateFormat(PI.firstInstallTime)

        // last modified
        binding.lastModify.text = setDateFormat(PI.lastUpdateTime)

        // features
        if (PI.reqFeatures != null)
            binding.reqFeature.text = getFeatures(PI.reqFeatures)
        else
            binding.reqFeature.text = "-"

        // uses-permission
        if (PI.requestedPermissions != null)
            binding.reqPermission.text = getPermissions(PI.requestedPermissions)
        else
            binding.reqPermission.text = "-"
    }
    private fun setDateFormat(time: Long): String {
        val date = Date(time)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return formatter.format(date)
    }

    // Convert string array to comma separated string
    private fun getPermissions(requestedPermissions: Array<String>?): String {
        return requestedPermissions?.joinToString(",\n") ?: ""
    }

    // Convert string array to comma separated string
    private fun getFeatures(reqFeatures: Array<FeatureInfo>?): String {
        return reqFeatures?.joinToString(",\n") ?: ""
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
                val intent = Intent(this, AppAllListActivity::class.java)
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