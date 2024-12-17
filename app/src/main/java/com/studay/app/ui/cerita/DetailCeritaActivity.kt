package com.studay.app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailCeritaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_cerita)

        // Ambil data dari Intent
        val judul = intent.getStringExtra("judul") ?: "Judul tidak tersedia"
        val deskripsi = intent.getStringExtra("deskripsi") ?: "Deskripsi tidak tersedia"
        val gambar = intent.getStringExtra("gambar") // Untuk gambar URL

        // Hubungkan data dengan View
        findViewById<TextView>(R.id.tvJudulDetail).text = judul
        findViewById<TextView>(R.id.tvDeskripsiDetail).text = deskripsi

        // Cek apakah gambar URL atau resource ID
        val imageView = findViewById<ImageView>(R.id.ivGambarDetail)
        if (!gambar.isNullOrEmpty() && gambar.startsWith("http")) {
            Glide.with(this)
                .load(gambar) // URL gambar
                .into(imageView)
        } else {
            // Gambar default jika URL tidak valid atau kosong
            imageView.setImageResource(R.drawable.ic_image)
        }
    }
}

