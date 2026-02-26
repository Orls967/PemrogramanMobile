package com.example.tugas2pemmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Halaman1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman1)

        val btnNext = findViewById<Button>(R.id.btnNext)

        btnNext.setOnClickListener {
            startActivity(Intent(this, Halaman2::class.java))
        }
    }
}