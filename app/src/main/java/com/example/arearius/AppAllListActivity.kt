package com.example.arearius

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.arearius.adapter.ApkAdapter
import com.example.arearius.data.AppData
import com.example.arearius.databinding.ActivityAppAllListBinding


class AppAllListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var binding :ActivityAppAllListBinding
    private lateinit var pm: PackageManager
    private lateinit var apkList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppAllListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        pm = getPackageManager()
        val packageList = packageManager
            .getInstalledPackages(PackageManager.GET_PERMISSIONS)
        val packageList1: List<PackageInfo> = ArrayList()

        
        apkList = findViewById(R.id.applist)
        apkList.adapter = ApkAdapter(this, packageList1, packageManager)

        apkList.onItemClickListener = this

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val packageInfo = parent?.getItemAtPosition(position) as PackageInfo
        val appData = application as AppData
        appData.setPackageInfo(packageInfo)

        val appInfo = Intent(applicationContext, ListDetailActivity::class.java)
        startActivity(appInfo)
    }
}