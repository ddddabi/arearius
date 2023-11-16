package com.example.arearius

import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.arearius.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
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
    }

}

