package com.studay.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.adapter.MenghitungAdapter
import com.studay.app.api.dataclass.Angka

class MenghitungActivity : AppCompatActivity() {

    private lateinit var rvAljabar: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnNext: ImageButton // Deklarasi btnNext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menghitung)

        // Inisialisasi komponen-komponen UI
        rvAljabar = findViewById(R.id.rvAngka)
        progressBar = findViewById(R.id.loadingCerita)
        btnNext = findViewById(R.id.btnNext) // Inisialisasi btnNext

        // Mengatur RecyclerView
        setupRecyclerView()

        // Memuat data angka
        loadAngkaData()

        // Menangani klik tombol Next
        btnNext.setOnClickListener {
            // Membuka MenghitungActivity2
            val intent = Intent(this, MenghitungActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        rvAljabar.layoutManager = GridLayoutManager(this, 4) // 4 kolom
    }

    private fun loadAngkaData() {
        progressBar.visibility = View.VISIBLE

        val angkaList = getAngkaList() // Mendapatkan daftar angka 1-20

        // Mengatur adapter untuk RecyclerView
        rvAljabar.adapter = MenghitungAdapter(angkaList) { angka ->
            Toast.makeText(this@MenghitungActivity, "Angka: ${angka.formula}", Toast.LENGTH_SHORT).show()
        }

        progressBar.visibility = View.GONE
    }

    // Fungsi untuk membuat daftar angka 1-20
    private fun getAngkaList(): List<Angka> {
        val angkaList = mutableListOf<Angka>()

        // Menambahkan angka 1-20 tanpa penjumlahan
        for (i in 1..20) {
            angkaList.add(Angka(id = i, formula = "$i", description = "Angka ke-$i"))
        }

        return angkaList
    }
}