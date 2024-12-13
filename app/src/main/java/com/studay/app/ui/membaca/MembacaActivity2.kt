package com.studay.app

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView // Tambahkan import untuk TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.adapter.MembacaAdapter2
import com.studay.app.api.dataclass.SukuKata

class MembacaActivity2 : AppCompatActivity() {

    private lateinit var rvAbjad: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvAbjad: TextView // Tambahkan deklarasi untuk TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membaca)

        rvAbjad = findViewById(R.id.rvAbjad)
        progressBar = findViewById(R.id.loadingCerita)
        tvAbjad = findViewById(R.id.tvAbjad) // Inisialisasi TextView

        // Mengubah teks pada TextView
        tvAbjad.text = "Suku Kata"

        setupRecyclerView()
        loadSukuKataData() // Memanggil fungsi untuk memuat data sukukata manual
    }

    // Fungsi untuk menyiapkan RecyclerView
    private fun setupRecyclerView() {
        rvAbjad.layoutManager = GridLayoutManager(this, 2) // 2 kolom
    }

    // Fungsi untuk memuat data sukukata A-Z secara manual
    private fun loadSukuKataData() {
        progressBar.visibility = View.VISIBLE

        val sukukataList = getSukuKataList() // Mendapatkan list sukukata A-Z secara manual

        // Mengatur adapter untuk RecyclerView
        rvAbjad.adapter = MembacaAdapter2(sukukataList) { sukukata ->
            Toast.makeText(this@MembacaActivity2, "SukuKata: ${sukukata.sukukata}", Toast.LENGTH_SHORT).show()
        }


        progressBar.visibility = View.GONE
    }

    // Fungsi untuk mendapatkan daftar suku kata
    private fun getSukuKataList(): List<SukuKata> {
        val sukuKataList = mutableListOf<SukuKata>()
        val konsonan = listOf("B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z")

        // Menambahkan suku kata berdasarkan konsonan dan vokal
        for (konsonanSukuKata in konsonan) {
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata A", suara = ""))
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata E", suara = ""))
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata I", suara = ""))
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata O", suara = ""))
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata U", suara = ""))
        }

        return sukuKataList
    }
}
