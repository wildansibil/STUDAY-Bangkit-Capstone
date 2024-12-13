package com.studay.app

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.adapter.MembacaAdapter
import com.studay.app.api.dataclass.Huruf

class MembacaActivity : AppCompatActivity() {

    private lateinit var rvAbjad: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membaca)

        rvAbjad = findViewById(R.id.rvAbjad)
        progressBar = findViewById(R.id.loadingCerita)

        setupRecyclerView()
        loadHurufData() // Memanggil fungsi untuk memuat data huruf manual
    }

    // Fungsi untuk menyiapkan RecyclerView
    private fun setupRecyclerView() {
        rvAbjad.layoutManager = GridLayoutManager(this, 4) // 2 kolom
    }

    // Fungsi untuk memuat data huruf A-Z secara manual
    private fun loadHurufData() {
        progressBar.visibility = View.VISIBLE

        val hurufList = getHurufList() // Mendapatkan list huruf A-Z secara manual

        // Mengatur adapter untuk RecyclerView
        rvAbjad.adapter = MembacaAdapter(hurufList) { huruf ->
            Toast.makeText(this@MembacaActivity, "Huruf: ${huruf.huruf}", Toast.LENGTH_SHORT).show()
        }

        progressBar.visibility = View.GONE
    }

    // Fungsi untuk membuat daftar huruf A-Z
    private fun getHurufList(): List<Huruf> {
        val hurufList = mutableListOf<Huruf>()

        // Menambahkan huruf A-Z secara manual
        for (char in 'A'..'Z') {
            hurufList.add(Huruf(huruf = char, suara = String())) // Mengabaikan suara
        }

        return hurufList
    }
}