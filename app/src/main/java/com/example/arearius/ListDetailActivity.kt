package com.example.arearius

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.FeatureInfo
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.arearius.data.AppData
import com.example.arearius.databinding.ActivityListDetailBinding
import java.io.File
import java.security.MessageDigest
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

        val appData = applicationContext as AppData
        PI = appData.packageInfo

        setValues()

        val intent = Intent(this, RestApiActivity::class.java)
        intent.putExtra("md5", getMD5Checksum(File(PI.applicationInfo.sourceDir)))
        binding.btntotal.setOnClickListener{startActivity(intent)}
    }

    private fun setValues() {

        // 아이콘 설정
        binding.appicon.setImageDrawable(packageManager.getApplicationIcon(packageManager.getApplicationInfo(PI.packageName, 0)))

        // APP name
        binding.applabel.text = packageManager.getApplicationLabel(PI.applicationInfo)
        binding.appnametitle.text = packageManager.getApplicationLabel(PI.applicationInfo)

        // package name
        binding.packageName.text = PI.packageName

        // version name
        binding.versionName.text = PI.versionName

        //md5
        binding.md5Label.text = getMD5Checksum(File(PI.applicationInfo.sourceDir))

        // path
        binding.path.text = PI.applicationInfo.sourceDir

        // first installation
        binding.insdate.text = setDateFormat(PI.firstInstallTime)

        // last modified
        binding.lastModify.text = setDateFormat(PI.lastUpdateTime)

        // uses-permission
        if (PI.requestedPermissions != null)
            binding.reqPermission.text = getPermissions(PI.requestedPermissions)
        else
            binding.reqPermission.text = "-"
    }

    fun getMD5Checksum(file: File): String {
        val digest = MessageDigest.getInstance("MD5")
        val inputStream = file.inputStream()

        val buffer = ByteArray(8192)
        var read: Int
        while (inputStream.read(buffer).also { read = it } > 0) {
            digest.update(buffer, 0, read)
        }

        val md5sum = digest.digest()
        val bigInt = StringBuilder(md5sum.size * 2)
        md5sum.forEach {
            bigInt.append(String.format("%02x", it))
        }

        return bigInt.toString()
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

}