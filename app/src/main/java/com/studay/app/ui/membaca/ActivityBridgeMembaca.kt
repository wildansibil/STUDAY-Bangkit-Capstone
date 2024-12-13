package com.studay.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView


class ActivityBridgeMembaca : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge_membaca)

        val cvAbjad = findViewById<CardView>(R.id.cvAbjad)
        val cvSukuKata = findViewById<CardView>(R.id.cvSukuKata)
        val cvKata = findViewById<CardView>(R.id.cvKata)

        // Menangani klik pada CardView "Abjad"
        cvAbjad.setOnClickListener {
            val intent = Intent(this, MembacaActivity::class.java)
            intent.putExtra("kategori", "Abjad")
            startActivity(intent)
        }

        // Menangani klik pada CardView "Suku Kata"
        cvSukuKata.setOnClickListener {
            val intent = Intent(this, MembacaActivity2::class.java)
            intent.putExtra("kategori", "Suku Kata")
            startActivity(intent)
        }

        // Menangani klik pada CardView "Kata"
        cvKata.setOnClickListener {
            val intent = Intent(this, MembacaActivity::class.java)
            intent.putExtra("kategori", "Kata")
            startActivity(intent)
        }
    }
}
