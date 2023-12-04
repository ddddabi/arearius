package com.example.arearius

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.arearius.databinding.ActivitySandboxResultBinding

class SandboxResultActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySandboxResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySandboxResultBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_sandbox_result)

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