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

class MenghitungActivity : AppCompatActivity() {

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
        rvAljabar.layoutManager = GridLayoutManager(this, 4) // 2 kolom
    }

    // Fungsi untuk memuat data angka 1-20
    private fun loadAngkaData() {
        progressBar.visibility = View.VISIBLE

        val angkaList = getAngkaList() // Mendapatkan daftar angka 1-20

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

