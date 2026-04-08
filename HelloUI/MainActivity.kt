package com.example.hellonama

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNama = findViewById<EditText>(R.id.etNama)
        val btnSapa = findViewById<Button>(R.id.btnSapa)
        val tvOutput = findViewById<TextView>(R.id.tvOutput)
        val btnMode = findViewById<Button>(R.id.btnMode)

        // Tombol Sapa
        btnSapa.setOnClickListener {
            val nama = etNama.text.toString()
            tvOutput.text = "Hello, $nama"
        }

        // Tombol Light / Dark Mode
        btnMode.setOnClickListener {
            isDarkMode = !isDarkMode

            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            } else {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
    }
}