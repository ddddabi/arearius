package com.example.arearius

import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.arearius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        var itemlist = arrayListOf<item>()

        val packageManager = this.packageManager
        val packages : List<PackageInfo> = packageManager.getInstalledPackages(0)

        for (info:PackageInfo in packages)
        {
            val iticon:Drawable = info.applicationInfo.loadIcon(packageManager)
            val it:item = item(info.applicationInfo.processName, iticon)
            itemlist.add(it)
        }
        val itemAdapter = MainListAdapter(this,itemlist)
        binding.mainListView.adapter = itemAdapter
    }
}