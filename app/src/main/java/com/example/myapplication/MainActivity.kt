package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uitugas1)

        val etNama = findViewById<EditText>(R.id.etNama)
        val btnSapa = findViewById<Button>(R.id.btnSapa)
        val tvOutput = findViewById<TextView>(R.id.tvOutput)
        val switchMode = findViewById<Switch>(R.id.switchMode)
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val isDark = (currentMode == AppCompatDelegate.MODE_NIGHT_YES)

        switchMode.isChecked = isDark
        switchMode.text = if (isDark) "Mode Terang" else "Mode Gelap"

        btnSapa.setOnClickListener {
            val nama = etNama.text.toString().trim()

            if (nama.isEmpty()) {
                tvOutput.text = "Masukkan nama dulu!"
            } else {
                tvOutput.text = "Hello, $nama!"
            }
        }

        switchMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchMode.text = "Mode Terang"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchMode.text = "Mode Gelap"
            }
        }
    }
}