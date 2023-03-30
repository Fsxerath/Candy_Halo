package com.example.candy_halo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.candy_halo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStartGame.setOnClickListener {
            startGame()
        }
    }

    fun startGame(){
        val intent = Intent(this, Candy_Halo::class.java)
        startActivity(intent)
    }
}