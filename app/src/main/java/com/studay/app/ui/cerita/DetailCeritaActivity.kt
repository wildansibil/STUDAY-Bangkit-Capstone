package com.studay.app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailCeritaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_cerita)

        // Ambil data dari Intent
        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("deskripsi")
        val gambar = intent.getIntExtra("gambar", 0)

        // Hubungkan data dengan View
        findViewById<TextView>(R.id.tvJudulDetail).text = judul
        findViewById<TextView>(R.id.tvDeskripsiDetail).text = deskripsi
        findViewById<ImageView>(R.id.ivGambarDetail).setImageResource(gambar)
    }
}
