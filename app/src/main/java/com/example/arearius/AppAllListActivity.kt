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

}