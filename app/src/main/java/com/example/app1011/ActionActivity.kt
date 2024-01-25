package com.example.app1011

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app1011.databinding.ActivityMainBinding
import com.example.app1011.databinding.ActivitySecondBinding
import com.example.app1011.databinding.ActivityThirdBinding

class ActionActivity : AppCompatActivity() {
    private lateinit var userViewModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actions)

        val flowerDataSet = DataRepository.flowers
        val flowerAdapter = FlowerAdapter(flowerDataSet)
        val recyclerView: RecyclerView = findViewById(R.id.rv_flower)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = flowerAdapter
    }
}
