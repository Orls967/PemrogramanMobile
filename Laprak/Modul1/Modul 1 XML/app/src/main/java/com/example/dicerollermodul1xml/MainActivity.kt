package com.example.dicerollermodul1xml

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dice1 = findViewById<ImageView>(R.id.dice1)
        val dice2 = findViewById<ImageView>(R.id.dice2)
        val button = findViewById<Button>(R.id.btnRoll)
        val result = findViewById<TextView>(R.id.resultText)

        button.setOnClickListener {

            val roll1 = Random.nextInt(1,7)
            val roll2 = Random.nextInt(1,7)

            dice1.setImageResource(getDiceImage(roll1))
            dice2.setImageResource(getDiceImage(roll2))

            if (roll1 == roll2) {
                result.text = "Selamat, anda dapat dadu double!"
            } else {
                result.text = "Anda belum beruntung!"
            }
        }
    }

    private fun getDiceImage(value: Int): Int {
        return when(value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}