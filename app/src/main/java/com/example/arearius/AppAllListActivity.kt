package com.example.arearius

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.arearius.adapter.ApkAdapter
import com.example.arearius.data.AppData
import com.example.arearius.databinding.ActivityAppAllListBinding


class AppAllListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var binding :ActivityAppAllListBinding
    private lateinit var pm: PackageManager

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppAllListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)	//툴바 사용 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)	//왼쪽 버튼 사용설정(기본은 뒤로가기)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.round_arrow_back_ios_new_24)	//왼쪽 버튼 메뉴로 아이콘 변경
        supportActionBar!!.setDisplayShowTitleEnabled(true)		//타이틀 보이게 설정

        pm = packageManager
        val packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        val packageList1 = mutableListOf<PackageInfo>()

        /* 시스템 앱을 필터링합니다 */
        for (pi in packageList) {
            val isSystem = isSystemPackage(pi)
            if (!isSystem) {
                packageList1.add(pi)
            }
        }
        binding.applist.adapter = ApkAdapter(this, packageList, packageManager)
        binding.applist.onItemClickListener = this
    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val packageInfo = parent?.getItemAtPosition(position) as PackageInfo

        // AppData 객체 초기화
        val appData = application as AppData

        // PackageInfo 설정
        appData.packageInfo = packageInfo

        val appInfo = Intent(applicationContext, ListDetailActivity::class.java)
        startActivity(appInfo)
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
                val intent = Intent(this, MainActivity::class.java)
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