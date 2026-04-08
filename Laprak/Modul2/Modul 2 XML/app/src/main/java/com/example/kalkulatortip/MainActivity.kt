package com.example.kalkulatortip

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etBill = findViewById<EditText>(R.id.etBill)
        val spinner = findViewById<Spinner>(R.id.spinnerTip)
        val switchRound = findViewById<Switch>(R.id.switchRound)
        val result = findViewById<TextView>(R.id.tvResult)

        // isi pilihan persen tip
        val tipOptions = arrayOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipOptions)
        spinner.adapter = adapter

        // function hitung tip
        fun calculateTip() {
            val billText = etBill.text.toString()

            if (billText.isEmpty()) {
                result.text = "Tip Amount: $0.00"
                return
            }

            val bill = billText.toDouble()
            val selected = spinner.selectedItem.toString()

            val percent = when (selected) {
                "15%" -> 0.15
                "18%" -> 0.18
                else -> 0.20
            }

            var tip = bill * percent

            if (switchRound.isChecked) {
                tip = ceil(tip)
            }

            result.text = "Tip Amount: %.2f".format(tip)
        }

        etBill.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateTip()
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                calculateTip()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        switchRound.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }
    }
}