package com.studay.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ActivityBridgeMenghitung : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge_menghitung)

        // Menginisialisasi CardView
        val cvAngka: CardView = findViewById(R.id.cvAngka)
        val cvPlusMin: CardView = findViewById(R.id.cvPlusMin)
        val cvLatihanMenghitung: CardView = findViewById(R.id.cvLatihanMenghitung)

        // Set listener untuk CardView Angka
        cvAngka.setOnClickListener {
            val intent = Intent(this, MenghitungActivity::class.java)
            startActivity(intent)
        }

        // Set listener untuk CardView Penjumlahan dan Pengurangan
        cvPlusMin.setOnClickListener {
            val intent = Intent(this, MenghitungActivity2::class.java)
            startActivity(intent)
        }

        // Set listener untuk CardView Latihan Menghitung
        cvLatihanMenghitung.setOnClickListener {
            val intent = Intent(this, MenghitungActivity::class.java)
            startActivity(intent)
        }
    }
}
