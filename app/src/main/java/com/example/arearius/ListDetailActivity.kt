package com.example.arearius

import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.arearius.data.AppData
import com.example.arearius.databinding.ActivityListDetailBinding
import java.io.File
import java.io.FileInputStream
import java.security.DigestInputStream
import java.text.SimpleDateFormat
import java.util.*
import java.security.MessageDigest



class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailBinding
    private lateinit var PI: PackageInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appData = applicationContext as AppData
        PI = appData.packageInfo

        setValues()

        val checksums = getFileChecksums(PI.applicationInfo.sourceDir)

        val intent = Intent(this, RestApiActivity::class.java)
        if (checksums != null) {
            intent.putExtra("md5", checksums.first)
            //intent.putExtra("sha1", checksums.second)
            //intent.putExtra("sha256", checksums.third)
        }
        binding.btntotal.setOnClickListener { startActivity(intent) }

        val intent2 = Intent(this, MetaApiActivity::class.java)
        if (checksums != null) {
            //intent2.putExtra("md5", checksums.first)
            intent2.putExtra("sha1", checksums.second)
            //intent2.putExtra("sha256", checksums.third)
        }
        binding.btnmeta.setOnClickListener { startActivity(intent2) }
    }

    private fun setValues() {

        // 아이콘 설정
        binding.appicon.setImageDrawable(packageManager.getApplicationIcon(packageManager.getApplicationInfo(PI.packageName, 0)))

        // APP name
        binding.applabel.text = packageManager.getApplicationLabel(PI.applicationInfo)
        binding.appnametitle.text = packageManager.getApplicationLabel(PI.applicationInfo)

        // package name
        if(PI.packageName!= null)
            binding.packageName.text = PI.packageName
        else
            binding.packageName.text = "-"

        // version name
        if(PI.versionName != null)
            binding.versionName.text = PI.versionName
        else
            binding.versionName.text = "-"

        // hash
        val checksums = getFileChecksums(PI.applicationInfo.sourceDir)

        if (checksums != null) {
            val md5Checksum = checksums.first
            val sha1Checksum = checksums.second
            val sha256Checksum = checksums.third
            //md5
            binding.md5Label.text = md5Checksum
            //sha-1
            binding.sha1Label.text = sha1Checksum
            //sha-256
            binding.sha256Label.text = sha256Checksum
        } else {
            //md5
            binding.md5Label.text = "-"
            //sha-1
            binding.sha1Label.text = "-"
            //sha-256
            binding.sha256Label.text = "-"
        }

        // path
        if(PI.applicationInfo.sourceDir != null)
            binding.path.text = PI.applicationInfo.sourceDir
        else
            binding.path.text = "-"

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
    fun getFileChecksums(filePath: String): Triple<String, String, String>? {
        val file = File(filePath)

        try {
            val md5Digest = MessageDigest.getInstance("MD5")
            val sha1Digest = MessageDigest.getInstance("SHA-1")
            val sha256Digest = MessageDigest.getInstance("SHA-256")

            FileInputStream(file).use { fileInputStream ->
                DigestInputStream(fileInputStream, md5Digest).use { digestInputStream ->
                    DigestInputStream(digestInputStream, sha1Digest).use { digestInputStream2 ->
                        DigestInputStream(digestInputStream2, sha256Digest).use { digestInputStream3 ->
                            val buffer = ByteArray(8192)
                            var bytesRead = digestInputStream3.read(buffer)
                            while (bytesRead != -1) {
                                bytesRead = digestInputStream3.read(buffer)
                            }
                        }
                    }
                }
            }

            val md5HashBytes = md5Digest.digest()
            val sha1HashBytes = sha1Digest.digest()
            val sha256HashBytes = sha256Digest.digest()

            val md5Checksum = bytesToHex(md5HashBytes)
            val sha1Checksum = bytesToHex(sha1HashBytes)
            val sha256Checksum = bytesToHex(sha256HashBytes)

            return Triple(md5Checksum, sha1Checksum, sha256Checksum)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexStringBuilder = StringBuilder()

        for (byte in bytes) {
            hexStringBuilder.append(String.format("%02x", byte))
        }

        return hexStringBuilder.toString()
    }

    private fun setDateFormat(time: Long): String {
        val date = Date(time)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return formatter.format(date)
    }

    private fun getPermissions(requestedPermissions: Array<String>?): String {
        return requestedPermissions?.joinToString(",\n") ?: ""
    }

}