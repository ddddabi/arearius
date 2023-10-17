package com.example.arearius

import android.content.pm.FeatureInfo
import android.content.pm.PackageInfo
import android.os.Bundle
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

        val appData = applicationContext as AppData
        PI = appData.packageInfo

        setValues()

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
}