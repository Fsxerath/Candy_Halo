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
        binding.easy.setOnClickListener {
            startGame("easy")
        }
        binding.normal.setOnClickListener {
            startGame("normal")
        }
        binding.hard.setOnClickListener {
            startGame("hard")
        }
    }

    fun startGame(difficulty:String){
        val intent = Intent(this, Candy_Halo::class.java)
        intent.putExtra("difficulty", difficulty)
        startActivity(intent)
    }
}