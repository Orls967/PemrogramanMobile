package com.example.tugas2pemmobile

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Halaman2 : AppCompatActivity() {

    private lateinit var tvAndroidVersion: TextView
    private lateinit var tvModel: TextView
    private lateinit var tvBrand: TextView
    private lateinit var tvKernel: TextView
    private lateinit var tvBattery: TextView
    private lateinit var tvNetwork: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAndroidVersion = findViewById(R.id.tvAndroidVersion)
        tvModel = findViewById(R.id.tvModel)
        tvBrand = findViewById(R.id.tvBrand)
        tvKernel = findViewById(R.id.tvKernel)
        tvBattery = findViewById(R.id.tvBattery)
        tvNetwork = findViewById(R.id.tvNetwork)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        showDeviceInfo()
    }

    private fun showDeviceInfo() {

        tvAndroidVersion.text = "Android Version: ${Build.VERSION.RELEASE}"

        tvModel.text = "Device Model: ${Build.MODEL}"

        tvBrand.text = "Manufacturer: ${Build.MANUFACTURER}"

        tvKernel.text = "Kernel Version: ${System.getProperty("os.version")}"

        val batteryIntent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

        val batteryPct = if (level != null && scale != null && scale > 0) {
            level * 100 / scale
        } else {
            -1
        }

        tvBattery.text = "Battery Level: $batteryPct%"

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetworkInfo

        val status = when {
            activeNetwork == null -> "Offline"
            activeNetwork.type == ConnectivityManager.TYPE_WIFI -> "WiFi"
            activeNetwork.type == ConnectivityManager.TYPE_MOBILE -> "Cellular"
            else -> "Unknown"
        }

        tvNetwork.text = "Network Status: $status"
    }
}