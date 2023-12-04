package com.example.arearius

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.example.arearius.data.AppData
import com.example.arearius.databinding.ActivityListDetailBinding
import com.example.arearius.databinding.ActivitySandboxBinding
import java.io.File
import java.io.FileInputStream
import java.security.DigestInputStream
import java.security.MessageDigest

class SandboxActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySandboxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySandboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detail4.visibility = View.GONE
        binding.detail5.visibility = View.GONE
        binding.btnsend2.visibility = View.GONE

        // 전송 버튼
        binding.btnsend.setOnClickListener {
            Toast.makeText(applicationContext, "APP-Sandbox 전송 완료", Toast.LENGTH_LONG).show()
            binding.detail1.visibility = View.GONE
            binding.detail2.visibility = View.GONE
            binding.detail3.visibility = View.GONE
            binding.btnsend.visibility = View.GONE

            binding.detail4.visibility = View.VISIBLE
            binding.detail5.visibility = View.VISIBLE
            binding.btnsend2.visibility = View.VISIBLE
        }
        binding.btnsend2.setOnClickListener {
            val intent = Intent(this, SandboxResultActivity::class.java)
            startActivity(intent)
        }

        // app detail 토탈 버튼
        binding.btndetail.setOnClickListener{
            val intent = Intent(this, ListDetailActivity::class.java)
            startActivity(intent)
        }

        // list 버튼
        binding.btnlist.setOnClickListener {
            val intent = Intent(this, AppAllListActivity::class.java)
            startActivity(intent)
        }

        // home 버튼
        binding.btnhome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}