package com.example.notificationapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notificationapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textV.text = intent.getStringExtra("firstname") ?: "EmptyExtra"
        binding.texttitle.text = intent.getStringExtra("title") ?: "title"
        binding.textdesc.text = intent.getStringExtra("desc") ?: "message"
    }
}