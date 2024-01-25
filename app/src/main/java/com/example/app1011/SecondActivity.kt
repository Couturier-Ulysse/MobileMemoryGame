package com.example.app1011

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.app1011.databinding.ActivityMainBinding
import com.example.app1011.databinding.ActivitySecondBinding
class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySecondBinding.inflate(layoutInflater)
        Log.i("Myapp", "activity launched")
        super.onCreate(savedInstanceState)
        Log.i("Myapp", "activity launched 2")
        setContentView(binding.root)
        Log.i("Myapp", "activity launched 3")
        setButtonsOnClickListener()
    }
    private fun setButtonsOnClickListener() {
        binding.two.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
