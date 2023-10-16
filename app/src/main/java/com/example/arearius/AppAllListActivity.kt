package com.example.arearius

import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.arearius.adapter.MainListAdapter
import com.example.arearius.databinding.ActivityAppAllListBinding

class AppAllListActivity : AppCompatActivity() {
    private lateinit var binding :ActivityAppAllListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppAllListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 앱 목록 출력
        var itemlist = arrayListOf<item>()

        val packageManager = this.packageManager
        val packages : List<PackageInfo> = packageManager.getInstalledPackages(0)

        for (info: PackageInfo in packages)
        {
            val iticon: Drawable = info.applicationInfo.loadIcon(packageManager)
            val it:item = item(info.applicationInfo.processName, iticon)
            itemlist.add(it)
        }
        val itemAdapter = MainListAdapter(this,itemlist)
        binding.mainListView.adapter = itemAdapter
    }
}