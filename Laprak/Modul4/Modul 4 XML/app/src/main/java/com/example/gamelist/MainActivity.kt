package com.example.gamelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        setContentView(R.layout.activity_main)

        Timber.d("MainActivity berhasil dijalankan")

        if (savedInstanceState == null) {

            Timber.d("ListFragment dimasukkan ke fragmentContainer")

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ListFragment())
                .commit()
        }
    }
}