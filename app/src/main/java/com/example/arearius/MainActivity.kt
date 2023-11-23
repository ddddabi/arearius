package com.example.arearius


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.widget.ProgressBar
import android.widget.TextView
import com.example.arearius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var totalSpaceTextView: TextView
    private lateinit var usedSpaceTextView: TextView
    private lateinit var freeSpaceTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.menuOne.setOnClickListener {
            val intent = Intent(this, AppAllListActivity::class.java)
            startActivity(intent)
        }

        binding.menuTwo.setOnClickListener {

        }
        
        // 용량
        progressBar = binding.progressBar
        totalSpaceTextView = findViewById(R.id.totalSpaceTextView)
        usedSpaceTextView = findViewById(R.id.usedSpaceTextView)
        freeSpaceTextView = findViewById(R.id.freeSpaceTextView)

        val storageInfo = getStorageInfo()

        val totalSpace = storageInfo.totalBytes
        val usedSpace = storageInfo.usedBytes
        val freeSpace = storageInfo.freeBytes

        val usedPercentage = (usedSpace.toDouble() / totalSpace.toDouble() * 100).toInt()

        progressBar.max = 100
        progressBar.progress = usedPercentage

        totalSpaceTextView.text = formatBytes(totalSpace)
        usedSpaceTextView.text = formatBytes(usedSpace)
        freeSpaceTextView.text = formatBytes(freeSpace)
    }

    private fun getStorageInfo(): StorageInfo {
        val stat = StatFs(Environment.getDataDirectory().path)
        val totalBytes = stat.totalBytes
        val freeBytes = stat.availableBytes
        val usedBytes = totalBytes - freeBytes

        return StorageInfo(totalBytes, usedBytes, freeBytes)
    }
    private fun formatBytes(bytes: Long): String {
        val unit = 1024
        if (bytes < unit) return "$bytes B"
        val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
        val pre = "KMGTPE"[exp - 1]
        return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
    }
    data class StorageInfo(
        val totalBytes: Long,
        val usedBytes: Long,
        val freeBytes: Long
    )
}

