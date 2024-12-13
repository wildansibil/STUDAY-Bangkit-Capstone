package com.studay.app

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.adapter.MenghitungAdapter
import com.studay.app.api.ApiService
import com.studay.app.api.RetrofitClient
import com.studay.app.api.dataclass.Angka
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenghitungActivity2 : AppCompatActivity() {

    private lateinit var rvAljabar: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menghitung)

        rvAljabar = findViewById(R.id.rvAngka)
        progressBar = findViewById(R.id.loadingCerita)

        setupRecyclerView()
        loadAngkaData() // Memanggil fungsi untuk memuat angka 1-20
    }

    private fun setupRecyclerView() {
        rvAljabar.layoutManager = GridLayoutManager(this, 2) // 2 kolom
    }

    // Fungsi untuk memuat data angka 1-20
    private fun loadAngkaData() {
        progressBar.visibility = View.VISIBLE

        val angkaList = getAngkaList() // Mendapatkan daftar angka 1-20

        rvAljabar.adapter = MenghitungAdapter(angkaList) { angka ->
            Toast.makeText(this@MenghitungActivity2, "Angka: ${angka.formula}", Toast.LENGTH_SHORT).show()
        }

        progressBar.visibility = View.GONE
    }

    // Fungsi untuk membuat daftar angka 1-20
    private fun getAngkaList(): List<Angka> {
        val angkaList = mutableListOf<Angka>()

        // Menambahkan penjumlahan 1 sampai 10
        for (i in 1..10) {
            angkaList.add(Angka(id = i, formula = "1 + $i = ${1 + i}", description = "Penjumlahan 1 + $i"))
            angkaList.add(Angka(id = i + 10, formula = "2 + $i = ${2 + i}", description = "Penjumlahan 2 + $i"))
            angkaList.add(Angka(id = i + 20, formula = "3 + $i = ${3 + i}", description = "Penjumlahan 3 + $i"))
            angkaList.add(Angka(id = i + 30, formula = "4 + $i = ${4 + i}", description = "Penjumlahan 4 + $i"))
            angkaList.add(Angka(id = i + 40, formula = "5 + $i = ${5 + i}", description = "Penjumlahan 5 + $i"))
            angkaList.add(Angka(id = i + 50, formula = "6 + $i = ${6 + i}", description = "Penjumlahan 6 + $i"))
            angkaList.add(Angka(id = i + 60, formula = "7 + $i = ${7 + i}", description = "Penjumlahan 7 + $i"))
            angkaList.add(Angka(id = i + 70, formula = "8 + $i = ${8 + i}", description = "Penjumlahan 8 + $i"))
            angkaList.add(Angka(id = i + 80, formula = "9 + $i = ${9 + i}", description = "Penjumlahan 9 + $i"))
            angkaList.add(Angka(id = i + 90, formula = "10 + $i = ${10 + i}", description = "Penjumlahan 10 + $i"))
        }

        // Menambahkan pengurangan 10 sampai 1, tanpa hasil negatif
        for (i in 1..10) {
            if (10 - i >= 0) angkaList.add(Angka(id = 100 + i, formula = "10 - $i = ${10 - i}", description = "Pengurangan 10 - $i"))
            if (9 - i >= 0) angkaList.add(Angka(id = 110 + i, formula = "9 - $i = ${9 - i}", description = "Pengurangan 9 - $i"))
            if (8 - i >= 0) angkaList.add(Angka(id = 120 + i, formula = "8 - $i = ${8 - i}", description = "Pengurangan 8 - $i"))
            if (7 - i >= 0) angkaList.add(Angka(id = 130 + i, formula = "7 - $i = ${7 - i}", description = "Pengurangan 7 - $i"))
            if (6 - i >= 0) angkaList.add(Angka(id = 140 + i, formula = "6 - $i = ${6 - i}", description = "Pengurangan 6 - $i"))
            if (5 - i >= 0) angkaList.add(Angka(id = 150 + i, formula = "5 - $i = ${5 - i}", description = "Pengurangan 5 - $i"))
            if (4 - i >= 0) angkaList.add(Angka(id = 160 + i, formula = "4 - $i = ${4 - i}", description = "Pengurangan 4 - $i"))
            if (3 - i >= 0) angkaList.add(Angka(id = 170 + i, formula = "3 - $i = ${3 - i}", description = "Pengurangan 3 - $i"))
            if (2 - i >= 0) angkaList.add(Angka(id = 180 + i, formula = "2 - $i = ${2 - i}", description = "Pengurangan 2 - $i"))
            if (1 - i >= 0) angkaList.add(Angka(id = 190 + i, formula = "1 - $i = ${1 - i}", description = "Pengurangan 1 - $i"))
        }

        return angkaList
    }



}

